package com.inventory.middle.client.plan.plan.service;

import com.inventory.middle.client.plan.plan.dto.ManualPlanOrderCreateDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueDetailDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueDetailPageReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderPageReqDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 计划订单 RPC 接口
 */
public interface PlanOrderRpcService {

    /** 手动创建计划订单 */
    SingleResponse<Boolean> createManualPlanOrder(ManualPlanOrderCreateDTO dto);

    /** 查询计划订单 */
    SingleResponse<PlanOrderDTO> queryPlanOrderById(Long id, String tenantId);

    /** 更新计划订单 */
    SingleResponse<Boolean> updatePlanOrder(PlanOrderDTO dto);

    /** 分页查询计划订单 */
    PageResponse<PlanOrderDTO> pageQueryPlanOrder(PlanOrderPageReqDTO dto);

    /** 确认订单 */
    SingleResponse<Boolean> confirmPlanOrder(Long id, String tenantId);

    /** 下发订单 */
    SingleResponse<Boolean> issuePlanOrder(PlanOrderIssueReqDTO dto);

    /** 分页查询下发详情 */
    PageResponse<PlanOrderIssueDetailDTO> pageQueryPlanOrderIssueDetail(PlanOrderIssueDetailPageReqDTO dto);
}
