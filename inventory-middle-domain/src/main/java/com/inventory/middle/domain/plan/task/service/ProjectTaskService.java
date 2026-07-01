package com.inventory.middle.domain.plan.task.service;

import java.util.List;

public interface ProjectTaskService {

    ProjectTaskBO apply(ProjectTaskBO bo);

    ProjectTaskBO query(String requestId, Long projectId, String taskNo);

    void notify(String requestId, String originalBody, String tempData,
                Long calResultCode, String optTarget,
                String reRequestId, String reTaskNo);

    ProjectTaskBO detail(String taskNo);
}
