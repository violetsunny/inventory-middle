package com.inventory.middle.application.service;

import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayRespBO;
import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 库存-供给QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventorySupplyQueryService {

    PageResponse<InventorySupplyDto> queryPage(InventorySupplyPageQuery pageQuery);

    InventorySupplyDto findById(Long id);

    /**
     * 按天聚合供给数量 Map<日期, 良品数量>（plan迁移：querySupplyInventory）
     * logicalPlantId 为 null 时不限逻辑仓
     */
    Map<LocalDate, BigDecimal> querySupplyByDay(InventorySupplyByDayQueryBO query);

    /**
     * 逾期供给汇总（plan迁移：queryOverdueSupplyInventory）
     * startTime~endTime 由调用方设定（通常 now-180d ~ now）
     */
    BigDecimal queryOverdueSupplyTotal(InventorySupplyByDayQueryBO query);
}

