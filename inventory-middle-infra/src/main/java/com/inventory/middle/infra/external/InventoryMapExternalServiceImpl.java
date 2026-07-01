package com.inventory.middle.infra.external;

import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.client.dto.map.QueryInventoryMapDTO;
import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.repository.InventoryMapRepository;
import com.inventory.middle.domain.service.external.RemoteInventoryMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;

/**
 * 库存 MAP 服务 Infra 实现（本地 DB 版）
 */
@Slf4j
@Service
public class InventoryMapExternalServiceImpl implements RemoteInventoryMapService {

    @Resource
    private InventoryMapRepository inventoryMapRepository;

    @Override
    public SingleResponse<InventoryMapDTO> queryInventoryMap(QueryInventoryMapDTO query) {
        if (query == null) {
            return SingleResponse.buildSuccess(null);
        }
        log.info("InventoryMapExternalServiceImpl.queryInventoryMap skuCode={} logicalPlantNo={} tenantId={}",
                query.getSkuCode(), query.getLogicalPlantNo(), query.getTenantId());
        InventoryMap entity = inventoryMapRepository.findBySkuAndPlant(
                query.getSkuCode(), query.getLogicalPlantNo(), query.getTenantId());
        if (entity == null) {
            return SingleResponse.buildSuccess(null);
        }
        InventoryMapDTO dto = new InventoryMapDTO();
        BeanUtils.copyProperties(entity, dto);
        return SingleResponse.buildSuccess(dto);
    }
}
