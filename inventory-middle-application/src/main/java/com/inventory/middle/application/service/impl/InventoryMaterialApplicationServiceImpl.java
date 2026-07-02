package com.inventory.middle.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.convertor.InventoryMaterialConvertor;
import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import com.inventory.middle.domain.model.entity.InventoryMaterial;
import com.inventory.middle.domain.repository.InventoryMaterialQueryParam;
import com.inventory.middle.domain.repository.InventoryMaterialRepository;
import com.inventory.middle.domain.repository.ListMaterialCodeQueryParam;
import com.inventory.middle.infra.persistence.entity.PageQueryMaterialPlantRefRequestPO;
import com.inventory.middle.infra.persistence.mapper.MaterialLogicalPlantRefMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 库存物料ApplicationService实现
 * 迁移自: com.enn.inventory.center.ext.biz.material.impl.InventoryMaterialDomainServiceImpl
 */
@Slf4j
@Service
public class InventoryMaterialApplicationServiceImpl implements InventoryMaterialApplicationService {

    @Resource
    private InventoryMaterialRepository inventoryMaterialRepository;

    @Resource
    private MaterialLogicalPlantRefMapper materialLogicalPlantRefMapper;

    @Resource
    private InventoryMaterialConvertor inventoryMaterialConvertor;

    @Override
    public SingleResponse<Boolean> batchCreate(List<InventoryMaterialCreateRequest> createRequestList) {
        log.info("InventoryMaterialApplicationServiceImpl.batchCreate size={}",
                createRequestList == null ? 0 : createRequestList.size());
        if (CollectionUtils.isEmpty(createRequestList)) {
            return SingleResponse.of(false);
        }
        for (InventoryMaterialCreateRequest r : createRequestList) {
            InventoryMaterial entity = inventoryMaterialConvertor.toEntity(r);
            inventoryMaterialRepository.store(entity);
        }
        return SingleResponse.of(true);
    }

    @Override
    public SingleResponse<Boolean> updateByMaterialCode(InventoryMaterialUpdateRequest updateRequest) {
        log.info("InventoryMaterialApplicationServiceImpl.updateByMaterialCode request={}", JSON.toJSONString(updateRequest));
        InventoryMaterial entity = inventoryMaterialConvertor.toEntity(updateRequest);
        boolean result = inventoryMaterialRepository.update(entity);
        return SingleResponse.of(result);
    }

    @Override
    public MultiResponse<InventoryMaterialDTO> listByMaterialCodeList(ListMaterialCodeRequest request) {
        ListMaterialCodeQueryParam queryParam = inventoryMaterialConvertor.toQueryParam(request);
        List<InventoryMaterialDTO> list = inventoryMaterialRepository.listByMaterialCodes(queryParam)
                .stream()
                .map(inventoryMaterialConvertor::toDTO)
                .collect(Collectors.toList());
        return MultiResponse.of(list);
    }

    @Override
    public PageResponse<InventoryMaterialDTO> pageList(InventoryMaterialPageRequest pageRequest) {
        log.info("InventoryMaterialApplicationServiceImpl.pageList request={}", JSON.toJSONString(pageRequest));
        InventoryMaterialQueryParam queryParam = inventoryMaterialConvertor.toQueryParam(pageRequest);
        List<InventoryMaterial> list = inventoryMaterialRepository.listByCondition(queryParam);
        List<InventoryMaterialDTO> respList = list.stream()
                .map(inventoryMaterialConvertor::toDTO)
                .collect(Collectors.toList());
        return PageResponse.of(respList, (long) respList.size(),
                pageRequest.getPageSize().longValue(), pageRequest.getPageNum().longValue());
    }

    /**
     * 按逻辑仓列表查询物料（plan 迁移：queryMaterialsByLogicalPlantNos）
     * 通过 material_logical_plant_ref 表查各逻辑仓绑定的物料编码
     * 优先返回 outMaterialCode（若与 materialCode 不同），否则返回 materialCode
     */
    @Override
    public Map<String, List<String>> listByLogicalPlantNos(List<String> logicalPlantNos, String tenantId) {
        Map<String, List<String>> result = new HashMap<>();
        if (CollectionUtils.isEmpty(logicalPlantNos)) {
            return result;
        }
        for (String logicalPlantNo : logicalPlantNos) {
            PageQueryMaterialPlantRefRequestPO requestPO = new PageQueryMaterialPlantRefRequestPO();
            requestPO.setTenantId(tenantId);
            requestPO.setLogicalPlantNo(logicalPlantNo);
            List<com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo> refList =
                    materialLogicalPlantRefMapper.listBy(requestPO);
            if (!CollectionUtils.isEmpty(refList)) {
                // 通过 material_logical_plant_ref 拿到 materialCode，再查 outMaterialCode
                List<String> materialCodes = refList.stream()
                        .map(com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo::getMaterialCode)
                        .filter(StringUtils::isNotBlank)
                        .distinct()
                        .collect(Collectors.toList());

                // 查询 outMaterialCode
                ListMaterialCodeQueryParam codeQueryParam = new ListMaterialCodeQueryParam();
                codeQueryParam.setTenantId(tenantId);
                codeQueryParam.setMaterialCodeList(materialCodes);
                List<InventoryMaterial> materials = inventoryMaterialRepository.listByMaterialCodes(codeQueryParam);

                // 构建 materialCode -> outMaterialCode 映射
                Map<String, String> codeMapping = new HashMap<>();
                if (!CollectionUtils.isEmpty(materials)) {
                    for (InventoryMaterial m : materials) {
                        codeMapping.put(m.getMaterialCode(), m.getOutMaterialCode());
                    }
                }

                List<String> codes = materialCodes.stream().map(mc -> {
                    String outCode = codeMapping.get(mc);
                    if (StringUtils.isNotBlank(outCode) && !StringUtils.equals(outCode, mc)) {
                        return outCode;
                    }
                    return mc;
                }).collect(Collectors.toList());

                result.put(logicalPlantNo, codes);
            }
        }
        return result;
    }

    /**
     * 按物料编码关键词模糊查询（plan 迁移：fuzzyQueryMaterialCode）
     * 通过 material_logical_plant_ref 按 logicalPlantNo + fuzzyMaterialCode 查询
     */
    @Override
    public List<InventoryMaterialDTO> fuzzyQueryByMaterialCodeAndLogicalPlant(
            String keyword, String logicalPlantNo, String tenantId, int maxSize) {
        if (StringUtils.isBlank(keyword) || StringUtils.isBlank(tenantId)) {
            return Collections.emptyList();
        }

        PageQueryMaterialPlantRefRequestPO requestPO = new PageQueryMaterialPlantRefRequestPO();
        requestPO.setTenantId(tenantId);
        requestPO.setLogicalPlantNo(logicalPlantNo);
        requestPO.setFuzzyMaterialCode(keyword);
        requestPO.setPageSize(maxSize);
        requestPO.setPageNum(1);

        List<com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo> refList =
                materialLogicalPlantRefMapper.listBy(requestPO);
        if (CollectionUtils.isEmpty(refList)) {
            return Collections.emptyList();
        }

        List<String> materialCodes = refList.stream()
                .map(com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo::getMaterialCode)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        ListMaterialCodeQueryParam codeQueryParam = new ListMaterialCodeQueryParam();
        codeQueryParam.setTenantId(tenantId);
        codeQueryParam.setMaterialCodeList(materialCodes);
        return inventoryMaterialRepository.listByMaterialCodes(codeQueryParam)
                .stream()
                .map(inventoryMaterialConvertor::toDTO)
                .collect(Collectors.toList());
    }
}
