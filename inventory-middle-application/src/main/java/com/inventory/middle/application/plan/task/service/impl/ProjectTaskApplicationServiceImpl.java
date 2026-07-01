package com.inventory.middle.application.plan.task.service.impl;

import com.inventory.middle.application.plan.task.service.ProjectTaskApplicationService;
import com.inventory.middle.domain.plan.task.service.ProjectTaskBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ProjectTaskApplicationServiceImpl implements ProjectTaskApplicationService {

    @Resource
    private com.inventory.middle.application.plan.task.service.ProjectTaskService projectTaskService;

    @Override
    public SingleResponse<Map<String, String>> projectTaskApply(
            String requestId, Long projectId, String projectType,
            String taskRuleBody, String taskDataBody) {
        ProjectTaskBO bo = projectTaskService.apply(requestId, projectId, projectType,
                taskRuleBody, taskDataBody);
        Map<String, String> result = new HashMap<>();
        result.put("requestId", bo.getRequestId());
        result.put("taskNo", bo.getTaskNo());
        return SingleResponse.of(result);
    }

    @Override
    public SingleResponse<Map<String, Object>> projectTaskQuery(
            String requestId, Long projectId, String taskNo) {
        ProjectTaskBO bo = projectTaskService.query(requestId, projectId, taskNo);
        Map<String, Object> result = new HashMap<>();
        result.put("requestId", bo.getRequestId());
        result.put("taskNo", bo.getTaskNo());
        result.put("taskResult", bo.getOriginalBody());
        return SingleResponse.of(result);
    }

    @Override
    public SingleResponse<Void> projectTaskNotify(
            String requestId, Long calResultCode, String optTarget,
            String reRequestId, String reTaskNo,
            String originalBody, String tempData) {
        projectTaskService.notify(requestId, calResultCode, optTarget,
                reRequestId, reTaskNo, originalBody, tempData);
        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<Map<String, Object>> projectTaskDetail(String taskNo) {
        ProjectTaskBO bo = projectTaskService.detail(taskNo);
        Map<String, Object> result = new HashMap<>();
        result.put("requestId", bo.getRequestId());
        result.put("taskNo", bo.getTaskNo());
        result.put("requestStatus", bo.getRequestStatus());
        result.put("requestBody", bo.getRequestBody());
        result.put("originalBody", bo.getOriginalBody());
        result.put("tempData", bo.getTempData());
        result.put("calResultCode", bo.getCalResultCode());
        result.put("optTarget", bo.getOptTarget());
        result.put("reRequestId", bo.getReRequestId());
        result.put("reTaskNo", bo.getReTaskNo());
        result.put("createTime", bo.getCreateTime());
        result.put("reCreateTime", bo.getReCreateTime());
        return SingleResponse.of(result);
    }
}
