package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;

import java.math.BigDecimal;

/**
 * 库存快照-实时QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventorySnapshotQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventorySnapshotDto> queryPage(InventorySnapshotPageQuery pageQuery);


    /**
     * 通过ID获取库存快照-实时
     *
     * @param id
     * @return
     */
    InventorySnapshotDto findById(Long id);


    /** 城燃库存分页（复用 queryPage，入口区分即可） */
    PageResponse<InventorySnapshotDto> pageListCityGas(InventorySnapshotPageQuery pageQuery);

    /** 按批次号分页查询 */
    PageResponse<InventorySnapshotDto> queryByBatchNo(InventorySnapshotPageQuery pageQuery);

    /** 实时库存（复用 queryPage） */
    PageResponse<InventorySnapshotDto> queryCurrentInventory(InventorySnapshotPageQuery pageQuery);

    /** 导出列表数据（供 Controller 调用后写 Excel） */
    java.util.List<InventorySnapshotDto> exportList(InventorySnapshotPageQuery pageQuery);

    /**
     * 精确查询当前库存良品数量（plan 迁移：queryCurrentInventory）
     * 按 materialCode + logicalPlantNo + tenantId 三条件汇总 unrestricted 数量
     *
     * @param materialCode   物料编码
     * @param logicalPlantNo 逻辑仓编码
     * @param tenantId       租户id
     * @return 当前良品库存总量，无记录返回 BigDecimal.ZERO
     */
    BigDecimal queryCurrentInventoryByCondition(String materialCode, String logicalPlantNo, String tenantId);

}
