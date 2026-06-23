/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.inventory.*;
import com.inventory.middle.domain.model.bo.inventorysnapshot.QueryCurrentInventoryReqBO;
import com.inventory.middle.domain.model.bo.inventorysnapshot.QueryInventoryBatchNoReqBO;

import java.util.List;

/**
 * 库存供需操作
 *
 * @author kll
 * @version $Id: InventoryCoreService, v 0.1 2021/6/19 11:06 Exp $
 */
public interface InventoryCoreService {


    /**
     * 查询快照库存批次
     *
     * @param req
     * @return
     */
    List<InventorySnapshotBO> queryInventoryBatchNo(QueryBatchNoReqBO req);

    /**
     * 库存调增 -- 新批次都添加
     *
     * @param inventorySupplyBOS
     */
    void adjustInventoryInc(List<InventorySupplyBO> inventorySupplyBOS);

    /**
     * 库存调增 -- 原有批次 supply添加 snapshot更新
     *
     * @param inventorySupplyBOS
     */
    void adjustInventoryIncOriginal(List<InventorySupplyBO> inventorySupplyBOS);

    /**
     * 查询库存--供
     *
     * @param queryMaterialInventoryBO
     * @return
     */
    List<InventorySupplyBO> queryInventorySupplyData(QueryMaterialInventoryBO queryMaterialInventoryBO);

    /**
     * 查询库存-快照
     *
     * @param query
     * @return
     */
    List<InventorySnapshotBO> queryInventoryData(QueryMaterialInventoryBO query);

    /**
     * 根据物料批次查询库存--供
     * @param reqBO
     * @return
     */
    List<InventorySupplyBO> querySupplyMaterialBatchNo(QueryCurrentInventoryReqBO reqBO);

    /**
     * 批次批量查询库存--供
     * @param bo
     * @return
     */
    List<InventorySupplyBO> querySupplyByBatchNos(QueryInventoryBatchNoReqBO bo);

    /**
     * 去除数据 removeBatchNo
     * @param
     */
    Boolean removeBatchNo(CoverBatchNoReqBO req);

    /**
     * 删除整个租户数据
     * @param tenantId
     * @return
     */
    Boolean removeTenantId(String tenantId);
}
