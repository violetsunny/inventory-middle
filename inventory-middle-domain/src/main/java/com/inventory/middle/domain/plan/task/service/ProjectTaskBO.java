package com.inventory.middle.domain.plan.task.service;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectTaskBO extends BaseBo {

    private Long id;

    private String requestId;

    private String taskNo;

    private Long projectId;

    private String projectType;

    private Integer requestStatus;

    private String requestBody;

    private String originalBody;

    private String tempData;

    private Long calResultCode;

    private String optTarget;

    private String reRequestId;

    private String reTaskNo;

    private String createUserId;

    private String updateUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime reCreateTime;

    private Integer isDelete;

    @Override
    public String toLog() {
        return "ProjectTaskBO{requestId=" + requestId + ", taskNo=" + taskNo + "}";
    }
}
