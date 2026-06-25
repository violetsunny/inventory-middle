package com.inventory.middle.interfaces.web.bff;

import com.inventory.middle.application.service.AccessoriesFlowCodeApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.Collections;

@Tag(name = "BFF 兼容：备件流转码（/code/accessoriesFlow）")
@CatchAndLog
@RestController
@RequestMapping("/code")
@Slf4j
public class AccessoriesFlowCodeBffController {

    @Resource
    private AccessoriesFlowCodeApplicationService accessoriesFlowCodeApplicationService;

    @Operation(summary = "备件流转码日志查询（BFF 兼容）")
    @PostMapping("/accessoriesFlow/logs")
    public PageResponse<Object> logs(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/accessoriesFlow/logs：middle 暂未实现，返回空列表");
        return PageResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "备件流转码打印（BFF 兼容）")
    @PostMapping("/accessoriesFlow/print")
    public SingleResponse<Object> print(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/accessoriesFlow/print：middle 暂未实现");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "备件流转码详情（BFF 兼容）")
    @PostMapping("/accessoriesFlow/detail")
    public SingleResponse<Object> detail(@RequestBody Object request) {
        log.warn("BFF 兼容路由 /code/accessoriesFlow/detail：middle 暂未实现");
        return SingleResponse.buildSuccess(null);
    }
}
