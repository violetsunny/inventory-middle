package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.types.InventoryMapId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 移动平均价Repository
 */
public interface InventoryMapRepository {

    PageResponse<InventoryMap> queryPage(PageQuery pageQuery, Map<String, Object> params);

    InventoryMap findById(InventoryMapId id);

    /** 按 skuCode + logicalPlantNo + tenantId 查询当前 MAP（精确匹配，取最新一条） */
    InventoryMap findBySkuAndPlant(String skuCode, String logicalPlantNo, String tenantId);

    boolean store(InventoryMap inventorymap);

    boolean update(InventoryMap inventorymap);

    boolean delete(List<InventoryMapId> ids);
}
