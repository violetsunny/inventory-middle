package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.SparePartApplicationService;
import com.inventory.middle.client.dto.material.CreateMaterialLogicalPlantRefReqDTO;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;
import com.inventory.middle.client.dto.sparepart.PageSparePartReqDTO;
import com.inventory.middle.client.dto.sparepart.PageSparePartResDTO;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.logicalPlant.ListLogicalPlantByIdListRequestBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.service.MaterialLogicalPlantRefCoreService;
import com.inventory.middle.domain.service.impl.LogicalPlantCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageResponse;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SparePartApplicationServiceImpl implements SparePartApplicationService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    private InventorySupplyQueryServiceImpl inventorySupplyQueryService;

    @Resource
    private LogicalPlantCoreService logicalPlantCoreService;

    @Resource
    private MaterialLogicalPlantRefCoreService materialLogicalPlantRefCoreService;

    @Override
    public PageResponse<PageSparePartResDTO> pageList(PageSparePartReqDTO reqDTO) {
        InventorySupplyPageQuery pageQuery = new InventorySupplyPageQuery();
        pageQuery.setMaterialCode(reqDTO.getMaterialCode());
        pageQuery.setTenantId(reqDTO.getTenantId());
        pageQuery.setPageNum(reqDTO.getPageNum());
        pageQuery.setPageSize(reqDTO.getPageSize());

        PageResponse<InventorySupplyDto> supplyPage = inventorySupplyQueryService.queryPage(pageQuery);
        List<PageSparePartResDTO> list = new ArrayList<>();
        if (supplyPage.getData() != null) {
            for (InventorySupplyDto dto : supplyPage.getData()) {
                PageSparePartResDTO res = new PageSparePartResDTO();
                res.setBatchNo(dto.getBatchNo());
                res.setBatchTime(dto.getBatchTime() != null ? dto.getBatchTime().format(DATE_FMT) : null);
                res.setBatchPrice(dto.getBatchPrice());
                res.setLogicalPlantName(dto.getLogicalPlantNo());
                list.add(res);
            }
        }
        return PageResponse.of(list, supplyPage.getTotalCount(), supplyPage.getPageSize(), supplyPage.getPageNum());
    }

    @Override
    public boolean sparePartSave(List<CreateMaterialLogicalPlantRefReqDTO> reqDTOList, String tenantId, String userId) {
        if (CollectionUtils.isEmpty(reqDTOList)) {
            throw new BusinessException("请求参数不能为空");
        }
        List<Long> logicalPlantIds = reqDTOList.stream()
                .filter(Objects::nonNull)
                .map(CreateMaterialLogicalPlantRefReqDTO::getLogicalPlantId)
                .collect(Collectors.toList());

        ListLogicalPlantByIdListRequestBO requestBO = new ListLogicalPlantByIdListRequestBO();
        requestBO.setTenantId(tenantId);
        requestBO.setIdList(logicalPlantIds);
        List<LogicalPlantBO> plantList = logicalPlantCoreService.listByPlantIdList(requestBO);
        if (CollectionUtils.isEmpty(plantList)) {
            throw new BusinessException("获取逻辑仓信息失败");
        }
        Map<Long, String> plantNoMap = plantList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(LogicalPlantBO::getId, LogicalPlantBO::getLogicalPlantNo, (a, b) -> a));

        List<CreateMaterialLogicalPlantRefBO> boList = reqDTOList.stream()
                .filter(Objects::nonNull)
                .map(dto -> {
                    CreateMaterialLogicalPlantRefBO bo = new CreateMaterialLogicalPlantRefBO();
                    bo.setTenantId(tenantId);
                    bo.setMaterialCode(dto.getMaterialCode());
                    bo.setLogicalPlantId(dto.getLogicalPlantId());
                    bo.setLogicalPlantNo(plantNoMap.get(dto.getLogicalPlantId()));
                    bo.setOperatorId(userId);
                    return bo;
                })
                .collect(Collectors.toList());

        return materialLogicalPlantRefCoreService.batchCreate(boList);
    }
}
