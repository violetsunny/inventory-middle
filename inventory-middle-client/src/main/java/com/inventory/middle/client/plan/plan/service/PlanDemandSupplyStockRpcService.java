package com.inventory.middle.client.plan.plan.service;

import com.inventory.middle.client.plan.plan.dto.DemandSupplyStockBoardDetailReqDTO;
import com.inventory.middle.client.plan.plan.dto.DemandSupplyStockBoardDetailResDTO;
import com.inventory.middle.client.plan.plan.dto.DemandSupplyStockBoardReqDTO;
import com.inventory.middle.client.plan.plan.dto.DemandSupplyStockBoardResDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 供需存看板 RPC 接口
 */
public interface PlanDemandSupplyStockRpcService {

    /** 供需存看板（分页） */
    PageResponse<DemandSupplyStockBoardResDTO> demandSupplyStockBoard(DemandSupplyStockBoardReqDTO reqDTO, int pageNum, int pageSize);

    /** 供需存看板详情 */
    SingleResponse<DemandSupplyStockBoardDetailResDTO> demandSupplyStockBoardDetail(DemandSupplyStockBoardDetailReqDTO reqDTO);
}
