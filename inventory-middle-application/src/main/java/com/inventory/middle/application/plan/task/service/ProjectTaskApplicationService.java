package com.inventory.middle.application.plan.task.service;

import top.kdla.framework.dto.SingleResponse;

import java.util.Map;

public interface ProjectTaskApplicationService {

    SingleResponse<Map<String, String>> projectTaskApply(
            String requestId, Long projectId, String projectType,
            String taskRuleBody, String taskDataBody);

    SingleResponse<Map<String, Object>> projectTaskQuery(
            String requestId, Long projectId, String taskNo);

    SingleResponse<Void> projectTaskNotify(
            String requestId, Long calResultCode, String optTarget,
            String reRequestId, String reTaskNo,
            String originalBody, String tempData);

    SingleResponse<Map<String, Object>> projectTaskDetail(String taskNo);
}
