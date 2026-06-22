package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 逻辑仓库表Repository
 */
public interface LogicalPlantRepository {

    PageResponse<LogicalPlant> queryPage(PageQuery pageQuery, Map<String, Object> params);

    LogicalPlant findById(LogicalPlantId id);

    /** 按逻辑仓库编码查询 */
    LogicalPlant findByLogicalPlantNo(String logicalPlantNo);

    /** 按 idList / noList 批量查询 */
    List<LogicalPlant> listByIdsOrNos(String tenantId, List<Long> idList, List<String> noList);

    boolean store(LogicalPlant logicalplant);

    boolean update(LogicalPlant logicalplant);

    boolean delete(List<LogicalPlantId> ids);

    /** 按物理仓库编码查询逻辑仓列表 */
    List<LogicalPlant> listByWarehouseNo(String warehouseNo);

    /** 按外部仓库编码查询逻辑仓（plan 迁移：queryLogicalPlantByOutPlantNo） */
    LogicalPlant findByOutPlantNo(String outPlantNo, String tenantId);
}
