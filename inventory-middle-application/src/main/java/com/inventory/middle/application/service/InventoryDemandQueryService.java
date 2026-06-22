package com.inventory.middle.application.service;

import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO;
import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 库存-需求QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryDemandQueryService {

    PageResponse<InventoryDemandDto> queryPage(InventoryDemandPageQuery pageQuery);

    InventoryDemandDto findById(Long id);

    /**
     * 按天聚合需求数量 Map<日期, 良品数量>（plan迁移：queryDemandInventory）
     */
    Map<LocalDate, BigDecimal> queryDemandByDay(InventoryDemandByDayQueryBO query);

    /**
     * 逾期需求汇总（plan迁移：queryOverdueDemandInventory）
     */
    BigDecimal queryOverdueDemandTotal(InventoryDemandByDayQueryBO query);
}

