package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.inventorydemand.PlanInventoryDemandOverdueQry;
import com.inventory.middle.client.dto.inventorydemand.PlanInventoryDemandOverdueResp;
import com.inventory.middle.client.dto.inventorydemand.PlanInventoryDemandQry;
import com.inventory.middle.client.dto.inventorydemand.PlanInventoryDemandResp;

/**
 * 需求出库发货服务
 * @author vincent.li
 * @date 2021/9/28
 */
public interface InventoryDemandService {

    /**
     * 远期demand查询接口
     * @param qry
     * @return
     */
    top.kdla.framework.dto.PageResponse<PlanInventoryDemandResp> queryPlanInventoryDemand(PlanInventoryDemandQry qry);

    /**
     * 计划出逾期查询接口
     * @param qry
     * @return
     */
    top.kdla.framework.dto.SingleResponse<PlanInventoryDemandOverdueResp> queryPlanInventoryDemandOverdue(PlanInventoryDemandOverdueQry qry);
}