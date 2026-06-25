package com.inventory.middle.interfaces.web.bff;

import com.inventory.middle.application.service.CodeApplyOrderApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.Collections;

@Tag(name = "BFF 兼容：备件流转码申请单（/code/applyOrder）")
@CatchAndLog
@RestController
@RequestMapping("/code/applyOrder")
@Slf4j
public class CodeApplyOrderBffController {

    @Resource
    private CodeApplyOrderApplicationService codeApplyOrderApplicationService;

    @Operation(summary = "经销商窜码申请单详情（BFF 兼容）")
    @PostMapping("/distributor/detailInfo")
    public SingleResponse<Object> distributorOrderInfo(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/applyOrder/distributor/detailInfo：middle 暂未实现");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "厂商窜码申请单详情（BFF 兼容）")
    @PostMapping("/manufacturer/detailInfo")
    public SingleResponse<Object> manufacturerOrderInfo(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/applyOrder/manufacturer/detailInfo：middle 暂未实现");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "经销商窜码申请单分页查询（BFF 兼容）")
    @PostMapping("/distributor/pageList")
    public PageResponse<Object> distributorPageList(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/applyOrder/distributor/pageList：middle 暂未实现");
        return PageResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "厂商窜码申请单分页查询（BFF 兼容）")
    @PostMapping("/manufacturer/pageList")
    public PageResponse<Object> manufacturerPageList(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/applyOrder/manufacturer/pageList：middle 暂未实现");
        return PageResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "申请单审批状态下拉列表（BFF 兼容）")
    @PostMapping("/approvalStatus/list")
    public SingleResponse<Object> approvalStatusList() {
        log.warn("BFF 兼容路由 /code/applyOrder/approvalStatus/list：middle 暂未实现");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }
}
