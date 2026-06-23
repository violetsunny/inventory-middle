package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.inventorysupply.PlanInventorySupplyOverdueQry;
import com.inventory.middle.client.dto.inventorysupply.PlanInventorySupplyOverdueResp;
import com.inventory.middle.client.dto.inventorysupply.PlanInventorySupplyQry;
import com.inventory.middle.client.dto.inventorysupply.PlanInventorySupplyResp;
// RDFA import removed;
// RDFA import removed;

/**
 * 库存供应入库服务
 * @author vincent.li
 * @date 2021/9/28
 */
public interface InventorySupplyService {

    /**
     * 远期supply查询接口
     * @param qry
     * @return
     */
    top.kdla.framework.dto.PageResponse<PlanInventorySupplyResp> queryPlanInventorySupply(PlanInventorySupplyQry qry);

    /**
     * 计划入库逾期查询接口
     * @param qry
     * @return
     */
    top.kdla.framework.dto.SingleResponse<PlanInventorySupplyOverdueResp> queryPlanInventorySupplyOverdue(PlanInventorySupplyOverdueQry qry);

}