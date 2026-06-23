package com.inventory.middle.interfaces.web.plan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

/**
 * 计划任务管理占位 Controller。
 */
@Tag(name = "任务计划管理")
@CatchAndLog
@RestController
@RequestMapping("/projectTaskManage")
@Slf4j
public class ProjectTaskManageController {

    @Operation(summary = "根据任务编号获取计划任务详情")
    @GetMapping("/projectTaskDetail")
    public SingleResponse<?> projectTaskDetail(@RequestParam("taskNo") String taskNo) {
        log.warn("TODO: 待接入 PlanTask - projectTaskDetail taskNo={}", taskNo);
        return SingleResponse.buildFailure("TODO", "计划任务服务待接入");
    }
}
