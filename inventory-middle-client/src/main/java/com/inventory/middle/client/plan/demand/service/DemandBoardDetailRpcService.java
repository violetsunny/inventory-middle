package com.inventory.middle.client.plan.demand.service;

import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailResDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialReqDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialResDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 需求看板 RPC 接口
 */
public interface DemandBoardDetailRpcService {

    PageResponse<DemandBoardDetailResDTO> selectDemandBoardDetailByPage(DemandBoardDetailReqDTO reqDTO, int pageNum, int pageSize);

    SingleResponse<MaterialResDTO> selectMaterialsByName(MaterialReqDTO reqDTO);
}
