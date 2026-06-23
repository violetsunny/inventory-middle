/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.inventorysnapshot.CurrentInventoryBalanceQry;
import com.inventory.middle.client.dto.inventorysnapshot.CurrentInventoryBalanceResp;
import com.inventory.middle.client.dto.inventorysnapshot.QueryInventoryReqDTO;
import com.inventory.middle.client.dto.inventorysnapshot.QueryInventoryResDTO;
// RDFA import removed;

/**
 * 库存查询
 * @author kll
 * @version $Id: InventoryService, v 0.1 2021/6/29 9:53 Exp $
 */
public interface InventoryService {
    /**
     * 查询库存信息
     * @param query
     * @return
     */
    top.kdla.framework.dto.SingleResponse<QueryInventoryResDTO> queryInventoryLogicalPlant(QueryInventoryReqDTO query);

    /**
     * 现有期末库存查询
     * @param qry
     * @return
     */
    top.kdla.framework.dto.SingleResponse<CurrentInventoryBalanceResp> queryCurrentInventoryBalance(CurrentInventoryBalanceQry qry);
}
