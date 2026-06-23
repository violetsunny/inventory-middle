package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayRespBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.transit.TransferTransitStockBO;

import java.util.List;

/**
 * @author holmes
 * @Classname InventorySupplyCoreService
 * @Description 物料库存供给表
 * @Date 2021/6/19 19:25
 */
public interface InventorySupplyCoreService {



    /**
     * 新增
     *
     * @param inventorySupplyPO
     */
    void insert(InventorySupplyBO inventorySupplyPO);


    /**
     * 批量创建
     * @param inventorySupplyPOList
     * @return
     */
    boolean batchCreate(List<InventorySupplyBO> inventorySupplyPOList);


    /**
     * 批量更新
     * @param inventorySupplyPOList
     * @return
     */
    boolean batchUpdate(List<InventorySupplyBO> inventorySupplyPOList);


    /**
     * 查询在途库存
     * @param pagePO
     * @return
     */
    List<InventorySupplyBO> queryInTransitStock(TransferTransitStockBO pagePO);


    /**
     * 设置成无效
     *
     * @param skuCode
     * @param batchNo
     * @param tenantId
     */
    void disable(String skuCode, String batchNo, String tenantId);

    /**
     * 设置成无效
     *
     * @param id
     */
    void disableById(Long id);

    /**
     * 调减
     *
     * @param skuCode
     * @param batchNo
     * @param tenantId
     */
    void adjustDown(String skuCode, String batchNo, String tenantId);


    /**
     * @param materialDocId
     * @param skuCode
     * @param batchNo
     * @param tenantId
     * @param locationId
     * @param logicalPlantNo
     * @return
     */
    List<InventorySupplyBO> find(Long materialDocId, String skuCode, String batchNo, String tenantId, Long locationId, String logicalPlantNo);

    List<InventorySupplyBO> pageListByMaterialCode(String tenantId, String materialCode, Integer pageNum, Integer pageSize);

    /**
     * 按天统计查询计划入库
     * @param queryBO
     * @return
     */
    List<InventorySupplyByDayRespBO> queryInventorySupplyByDay(InventorySupplyByDayQueryBO queryBO);
}
