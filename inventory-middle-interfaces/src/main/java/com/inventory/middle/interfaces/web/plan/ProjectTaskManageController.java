package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.application.plan.task.service.ProjectTaskApplicationService;
import com.inventory.middle.interfaces.web.plan.vo.ProjectTaskDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Tag(name = "任务计划管理")
@CatchAndLog
@RestController
@RequestMapping("/projectTaskManage")
@Slf4j
public class ProjectTaskManageController {

    @Resource
    private ProjectTaskApplicationService projectTaskApplicationService;

    @Operation(summary = "根据任务编号获取计划任务详情")
    @GetMapping("/projectTaskDetail")
    public SingleResponse<ProjectTaskDetailVO> projectTaskDetail(@RequestParam("taskNo") String taskNo) {
        SingleResponse<Map<String, Object>> result = projectTaskApplicationService.projectTaskDetail(taskNo);
        if (!result.isSuccess()) {
            return SingleResponse.buildFailure(result.getCode(), result.getMessage());
        }
        ProjectTaskDetailVO vo = new ProjectTaskDetailVO();
        Map<String, Object> data = result.getData();
        vo.setRequestId((String) data.get("requestId"));
        vo.setTaskNo((String) data.get("taskNo"));
        vo.setRequestStatus((Integer) data.get("requestStatus"));
        vo.setRequestBody((String) data.get("requestBody"));
        vo.setOriginalBody((String) data.get("originalBody"));
        vo.setTempData((String) data.get("tempData"));
        vo.setCalResultCode((Long) data.get("calResultCode"));
        vo.setOptTarget((String) data.get("optTarget"));
        vo.setReRequestId((String) data.get("reRequestId"));
        vo.setReTaskNo((String) data.get("reTaskNo"));
        vo.setCreateTime((LocalDateTime) data.get("createTime"));
        vo.setReCreateTime((LocalDateTime) data.get("reCreateTime"));
        return SingleResponse.of(vo);
    }
}
