package com.inventory.middle.client.plan.config.service;

import com.inventory.middle.client.plan.config.dto.DemandSupplyStockBoardDetailReqDTO;
import com.inventory.middle.client.plan.config.dto.DemandSupplyStockBoardDetailResDTO;
import com.inventory.middle.client.plan.config.dto.DemandSupplyStockBoardReqDTO;
import com.inventory.middle.client.plan.config.dto.DemandSupplyStockBoardResDTO;
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
