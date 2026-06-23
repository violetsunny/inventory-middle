package com.inventory.middle.application.plan.demand.service;

import com.inventory.middle.application.plan.demand.bo.DemandBoardDetailReqBO;
import com.inventory.middle.application.plan.demand.bo.DemandBoardDetailResBO;
import com.inventory.middle.application.plan.demand.bo.MaterialReqBO;
import com.inventory.middle.application.plan.demand.bo.MaterialResultBO;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

/**
 * @author zhouxinzhong
 * @date: 2021/9/29 16:55
 */
public interface DemandBoardDetailService {

    /**
     * 需求看板分页查询
     * @param reqBO
     * @return
     */
    PageResponse<DemandBoardDetailResBO> selectDemandBoardDetailByPage(DemandBoardDetailReqBO reqBO, int pageNum, int pageSize);

    /**
     * 查询所有物料
     * @return
     */
    List<MaterialResultBO> selectMaterialsByName(MaterialReqBO reqBO);
}
