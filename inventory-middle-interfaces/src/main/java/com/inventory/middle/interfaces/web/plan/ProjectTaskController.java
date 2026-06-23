package com.inventory.middle.interfaces.web.plan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

/**
 * 计划任务占位 Controller。
 */
@Tag(name = "任务计划API")
@CatchAndLog
@RestController
@RequestMapping("/projectTask")
@Slf4j
public class ProjectTaskController {

    @Operation(summary = "计划任务申请")
    @PostMapping("/projectTaskApply")
    public SingleResponse<?> projectTaskApply(@RequestBody Object reqDTO) {
        log.warn("TODO: 待接入 PlanTask - projectTaskApply");
        return SingleResponse.buildFailure("TODO", "计划任务服务待接入");
    }

    @Operation(summary = "计划任务查询")
    @PostMapping("/projectTaskQuery")
    public SingleResponse<?> projectTaskQuery(@RequestBody Object reqDTO) {
        log.warn("TODO: 待接入 PlanTask - projectTaskQuery");
        return SingleResponse.buildFailure("TODO", "计划任务服务待接入");
    }

    @Operation(summary = "计划任务通知")
    @PostMapping("/projectTaskNotify")
    public SingleResponse<?> projectTaskNotify(@RequestBody Object reqDTO) {
        log.warn("TODO: 待接入 PlanTask - projectTaskNotify");
        return SingleResponse.buildFailure("TODO", "计划任务服务待接入");
    }
}
