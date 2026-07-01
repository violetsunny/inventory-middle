package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "计划任务申请参数")
public class ProjectTaskApplyReqDTO implements Serializable {

    @Schema(description = "流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestId;

    @Schema(description = "方案ID")
    private Long projectId;

    @Schema(description = "任务类型")
    private String projectType;

    @Schema(description = "任务规则参数")
    private String taskRule;

    @Schema(description = "任务数据参数")
    private String taskData;
}
