package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.application.plan.plan.service.PlanOrderApplicationService;
import com.inventory.middle.client.plan.plan.dto.PlanOrderDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueDetailDTO;
import com.inventory.middle.interfaces.support.UserContext;
import com.inventory.middle.interfaces.support.UserContextHolder;
import com.inventory.middle.interfaces.web.plan.dto.IssuePlanOrderReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.ManualPlanOrderCreateReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanOrderIssueDetailPageQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanOrderPageQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanOrderUpdateReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.PageQueryPlanOrderIssueDetailResVO;
import com.inventory.middle.interfaces.web.plan.vo.PlanOrderPageQueryResVO;
import com.inventory.middle.interfaces.web.plan.vo.PlanOrderQueryResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 计划订单 Controller。
 */
@Tag(name = "计划订单管理")
@CatchAndLog
@RestController
@RequestMapping("/planOrder")
@Slf4j
public class PlanOrderController {

    @Resource
    private PlanOrderApplicationService planOrderApplicationService;

    @Operation(summary = "手动创建计划订单")
    @PostMapping("/createManualPlanOrder")
    public SingleResponse<Boolean> createManualPlanOrder(@RequestBody ManualPlanOrderCreateReqDTO reqDTO) throws ParseException {
        return planOrderApplicationService.createManualPlanOrder(PlanOrderWebConvertor.toCreateDTO(reqDTO, currentUser()));
    }

    @Operation(summary = "查询计划订单")
    @PostMapping("/queryPlanOrder")
    public SingleResponse<PlanOrderQueryResVO> queryPlanOrder(@RequestParam("planOrderId") Long planOrderId) {
        PlanOrderDTO dto = planOrderApplicationService.queryPlanOrderById(planOrderId, UserContextHolder.getTenantId()).getData();
        return SingleResponse.buildSuccess(PlanOrderWebConvertor.toQueryVO(dto));
    }

    @Operation(summary = "更新计划订单")
    @PostMapping("/updatePlanOrder")
    public SingleResponse<Boolean> updatePlanOrder(@RequestBody PlanOrderUpdateReqDTO reqDTO) throws ParseException {
        return planOrderApplicationService.updatePlanOrder(PlanOrderWebConvertor.toUpdateDTO(reqDTO, currentUser()));
    }

    @Operation(summary = "下发计划订单")
    @PostMapping("/issuePlanOrder")
    public SingleResponse<Boolean> issuePlanOrder(@RequestBody IssuePlanOrderReqDTO reqDTO) {
        return planOrderApplicationService.issuePlanOrder(PlanOrderWebConvertor.toIssueDTO(reqDTO, currentUser()));
    }

    @Operation(summary = "确认计划订单")
    @PostMapping("/confirmPlanOrder")
    public SingleResponse<Boolean> confirmPlanOrder(@RequestParam("planOrderId") Long planOrderId) {
        return planOrderApplicationService.confirmPlanOrder(planOrderId, UserContextHolder.getTenantId());
    }

    @Operation(summary = "分页查询计划订单")
    @PostMapping("/pageQueryPlanOrder")
    public PageResponse<PlanOrderPageQueryResVO> pageQueryPlanOrder(@RequestBody PlanOrderPageQueryReqDTO reqDTO) {
        PageResponse<PlanOrderDTO> result = planOrderApplicationService.pageQueryPlanOrder(
                PlanOrderWebConvertor.toPageDTO(reqDTO, UserContextHolder.getTenantId()));
        List<PlanOrderPageQueryResVO> data = result.getData().stream()
                .map(PlanOrderWebConvertor::toPageVO)
                .collect(Collectors.toList());
        return PageResponse.of(data, result.getTotalCount(), result.getPageSize(), result.getPageNum());
    }

    @Operation(summary = "分页查询计划订单下发详情")
    @PostMapping("/pageQueryPlanOrderIssueDetail")
    public PageResponse<PageQueryPlanOrderIssueDetailResVO> pageQueryPlanOrderIssueDetail(@RequestBody PlanOrderIssueDetailPageQueryReqDTO reqDTO) {
        PageResponse<PlanOrderIssueDetailDTO> result = planOrderApplicationService.pageQueryPlanOrderIssueDetail(
                PlanOrderWebConvertor.toIssueDetailPageDTO(reqDTO, UserContextHolder.getTenantId()));
        List<PageQueryPlanOrderIssueDetailResVO> data = result.getData().stream()
                .map(PlanOrderWebConvertor::toIssueDetailVO)
                .collect(Collectors.toList());
        return PageResponse.of(data, result.getTotalCount(), result.getPageSize(), result.getPageNum());
    }

    private UserContext currentUser() {
        return UserContextHolder.get();
    }
}
