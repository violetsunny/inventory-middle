package com.inventory.middle.application.plan.order.service;

import com.inventory.middle.client.plan.config.service.PlanOrderRpcService;

import java.util.List;

/**
 * 计划订单应用服务接口。
 */
public interface PlanOrderApplicationService extends PlanOrderRpcService {

    List<Long> queryOverduePlanOrderIds();

    void changeOverduePlanOrderStatus(List<Long> ids);
}
