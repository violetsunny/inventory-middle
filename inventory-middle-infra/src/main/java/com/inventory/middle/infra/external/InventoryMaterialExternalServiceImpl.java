package com.inventory.middle.infra.external;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.domain.service.external.RemoteInventoryMaterialService;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialDo;
import com.inventory.middle.infra.persistence.entity.ListMaterialCodeParamPO;
import com.inventory.middle.infra.persistence.mapper.InventoryMaterialMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存物料服务 Infra 实现（本地 DB 版）
 * <p>
 * 原 ext 模块通过 Dubbo 调用 inventory-center，现 inventory-center 已迁入，直接查本地 InventoryMaterialMapper。
 */
@Slf4j
@Service
public class InventoryMaterialExternalServiceImpl implements RemoteInventoryMaterialService {

    @Resource
    private InventoryMaterialMapper inventoryMaterialMapper;

    @Override
    public ArrayList<InventoryMaterialDTO> listByMaterialCodeList(
            String tenantId, List<String> materialCodeList, List<String> outMaterialCodeList) {
        if (CollectionUtils.isEmpty(materialCodeList) && CollectionUtils.isEmpty(outMaterialCodeList)) {
            return new ArrayList<>();
        }
        ListMaterialCodeParamPO param = new ListMaterialCodeParamPO();
        param.setTenantId(tenantId);
        param.setMaterialCodeList(materialCodeList);
        param.setOutMaterialCodeList(outMaterialCodeList);
        List<InventoryMaterialDo> doList = inventoryMaterialMapper.listByMaterialCodeList(param);
        log.info("InventoryMaterialExternalServiceImpl.listByMaterialCodeList tenantId={} size={}", tenantId, doList.size());
        return doList.stream().map(d -> {
            InventoryMaterialDTO dto = new InventoryMaterialDTO();
            BeanUtils.copyProperties(d, dto);
            return dto;
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
