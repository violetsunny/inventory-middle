package com.inventory.middle.application.plan.demand.service;

import com.inventory.middle.application.plan.demand.bo.OrderDemandBO;

import java.util.List;

/**
 * @author xiaokang
 */
public interface ProjectOrderDemandService {

    /**
     * 创建项目计划订单
     * @param orderPlanBOList
     * @return
     */
    boolean insertProjectOrderPlan(List<OrderDemandBO> orderPlanBOList);
}
