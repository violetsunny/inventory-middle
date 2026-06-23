package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandBO;
import com.inventory.middle.domain.model.bo.inventory.PlanOutboundInventoryQueryBO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author holmes
 * @Classname InventoryDemandCoreService
 * @Description 物料库存需求表
 * @Date 2021/6/21 22:50
 */
public interface InventoryDemandCoreService {

    /**
     * 冲销
     *
     * @param mdocId
     * @param skuCode
     * @param batchNo
     * @param number
     */
    void reverse(Long mdocId, String skuCode, String batchNo, BigDecimal number);

    /**
     * 新增
     *
     * @param inventoryDemand
     */
    void create(InventoryDemandBO inventoryDemand);

    /**
     * 查找
     *
     * @param materialDocId
     * @param skuCode
     * @param batchNo
     * @param tenantId
     * @param locationId
     * @param logicalPlantNo
     * @return
     */
    List<InventoryDemandBO> find(Long materialDocId, String skuCode, String batchNo, String tenantId, Long locationId, String logicalPlantNo);

    /**
     * 查找批次号
     *
     * @param skuCode
     * @param batchNo
     * @param tenantId
     * @return
     */
    List<InventoryDemandBO> findByBatchNo(String skuCode, String batchNo, String tenantId);

    /**
     * 查询计划出库的按天统计
     * @param queryBO
     * @return
     */
    List<InventoryDemandByDayRespBO> queryInventoryDemandByDay(InventoryDemandByDayQueryBO queryBO);


    /**
     * 查询计划出库
     * @param queryPO
     * @return List<InventoryDemandBO>
     */
    List<InventoryDemandBO> queryPlanOutboundInventory(PlanOutboundInventoryQueryBO queryPO);
}
