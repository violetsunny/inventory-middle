package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.CodeApplyOrderApplicationService;
import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderApprovalRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderCreateRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderInfoRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderPageRequest;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderCreateResponse;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 码申请单Controller
 * 迁移自: CodeApplyOrderService (Dubbo Facade)
 */
@Tag(name = "码申请单管理")
@RestController
@RequestMapping("/code-apply-order")
@Slf4j
@CatchAndLog
public class CodeApplyOrderController {

    @Resource
    private CodeApplyOrderApplicationService codeApplyOrderApplicationService;

    @Operation(summary = "码申请单创建")
    @PostMapping("/create")
    public SingleResponse<CodeApplyOrderCreateResponse> create(@RequestBody CodeApplyOrderCreateRequest createRequest) {
        return codeApplyOrderApplicationService.create(createRequest);
    }

    @Operation(summary = "申请单审批")
    @PostMapping("/approval")
    public SingleResponse<Boolean> approval(@RequestBody CodeApplyOrderApprovalRequest approvalRequest) {
        return codeApplyOrderApplicationService.approval(approvalRequest);
    }

    @Operation(summary = "申请单信息")
    @PostMapping("/info")
    public SingleResponse<CodeApplyOrderInfoResponse> getApplyOrderInfo(@RequestBody CodeApplyOrderInfoRequest infoRequest) {
        return codeApplyOrderApplicationService.getApplyOrderInfo(infoRequest);
    }

    @Operation(summary = "申请单分页查询")
    @PostMapping("/page")
    public PageResponse<CodeApplyOrderDTO> pageList(@RequestBody CodeApplyOrderPageRequest pageRequest) {
        return codeApplyOrderApplicationService.pageList(pageRequest);
    }
}
