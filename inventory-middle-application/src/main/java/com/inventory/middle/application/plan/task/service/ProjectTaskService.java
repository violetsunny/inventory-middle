package com.inventory.middle.application.plan.task.service;

import com.inventory.middle.application.plan.task.service.ProjectTaskService;
import com.inventory.middle.domain.plan.task.service.ProjectTaskBO;

public interface ProjectTaskService {

    ProjectTaskBO apply(String requestId, Long projectId, String projectType,
                        String taskRuleBody, String taskDataBody);

    ProjectTaskBO query(String requestId, Long projectId, String taskNo);

    void notify(String requestId, Long calResultCode, String optTarget,
                String reRequestId, String reTaskNo,
                String originalBody, String tempData);

    ProjectTaskBO detail(String taskNo);
}
