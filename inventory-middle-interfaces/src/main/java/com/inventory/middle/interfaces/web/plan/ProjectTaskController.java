package com.inventory.middle.interfaces.web.plan;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.task.service.ProjectTaskApplicationService;
import com.inventory.middle.interfaces.web.plan.dto.ProjectTaskApplyReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.ProjectTaskNotifyReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.ProjectTaskQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.ProjectTaskApplyResVO;
import com.inventory.middle.interfaces.web.plan.vo.ProjectTaskQueryResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

@Tag(name = "任务计划API")
@CatchAndLog
@RestController
@RequestMapping("/projectTask")
@Slf4j
public class ProjectTaskController {

    @Resource
    private ProjectTaskApplicationService projectTaskApplicationService;

    @Operation(summary = "计划任务申请")
    @PostMapping("/projectTaskApply")
    public SingleResponse<ProjectTaskApplyResVO> projectTaskApply(@RequestBody ProjectTaskApplyReqDTO reqDTO) {
        String taskRuleBody = reqDTO.getTaskRule() != null ? JSON.toJSONString(reqDTO.getTaskRule()) : null;
        String taskDataBody = reqDTO.getTaskData() != null ? JSON.toJSONString(reqDTO.getTaskData()) : null;
        SingleResponse<java.util.Map<String, String>> result = projectTaskApplicationService.projectTaskApply(
                reqDTO.getRequestId(), reqDTO.getProjectId(), reqDTO.getProjectType(),
                taskRuleBody, taskDataBody);
        if (!result.isSuccess()) {
            return SingleResponse.buildFailure(result.getCode(), result.getMessage());
        }
        ProjectTaskApplyResVO vo = new ProjectTaskApplyResVO();
        vo.setRequestId(result.getData().get("requestId"));
        vo.setTaskNo(result.getData().get("taskNo"));
        return SingleResponse.of(vo);
    }

    @Operation(summary = "计划任务查询")
    @PostMapping("/projectTaskQuery")
    public SingleResponse<ProjectTaskQueryResVO> projectTaskQuery(@RequestBody ProjectTaskQueryReqDTO reqDTO) {
        SingleResponse<java.util.Map<String, Object>> result = projectTaskApplicationService.projectTaskQuery(
                reqDTO.getRequestId(), reqDTO.getProjectId(), reqDTO.getTaskNo());
        if (!result.isSuccess()) {
            return SingleResponse.buildFailure(result.getCode(), result.getMessage());
        }
        ProjectTaskQueryResVO vo = new ProjectTaskQueryResVO();
        vo.setRequestId((String) result.getData().get("requestId"));
        vo.setTaskNo((String) result.getData().get("taskNo"));
        vo.setTaskResult(result.getData().get("taskResult"));
        return SingleResponse.of(vo);
    }

    @Operation(summary = "计划任务通知")
    @PostMapping("/projectTaskNotify")
    public SingleResponse<Void> projectTaskNotify(@RequestBody ProjectTaskNotifyReqDTO reqDTO) {
        String originalBody = reqDTO.getOriginalBody() != null ? JSON.toJSONString(reqDTO.getOriginalBody()) : null;
        String tempData = reqDTO.getTempData() != null ? JSON.toJSONString(reqDTO.getTempData()) : null;
        return projectTaskApplicationService.projectTaskNotify(
                reqDTO.getRequestId(), reqDTO.getCalResultCode(), reqDTO.getOptTarget(),
                reqDTO.getReRequestId(), reqDTO.getReTaskNo(),
                originalBody, tempData);
    }
}
