package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 库存快照-实时Repository
 */
public interface InventorySnapshotRepository {

    PageResponse<InventorySnapshot> queryPage(PageQuery pageQuery, Map<String, Object> params);

    InventorySnapshot findById(InventorySnapshotId id);

    /** 按物料合计维度查询（对应 Mapper.queryMaterialTotal） */
    List<InventorySnapshot> queryMaterialTotal(Map<String, Object> params);

    /** 按物料+逻辑仓库查询（对应 Mapper.queryByMaterialAndLogical） */
    List<InventorySnapshot> queryByMaterialAndLogical(Map<String, Object> params);

    /** 按 params 批量查询列表 */
    List<InventorySnapshot> queryList(Map<String, Object> params);

    boolean store(InventorySnapshot inventorysnapshot);

    boolean update(InventorySnapshot inventorysnapshot);

    boolean delete(List<InventorySnapshotId> ids);

    /** 按 ID 禁用快照（deleted=1） */
    boolean disableById(Long id);

    /** 扣减库存数量，stockType 对应 StockTypeEnum.code：1=良品 2=残次品 3=质检品 */
    boolean adjustDown(Long id, BigDecimal number, Integer stockType);
}
