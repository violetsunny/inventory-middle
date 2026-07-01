package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "计划任务查询参数")
public class ProjectTaskQueryReqDTO implements Serializable {

    @Schema(description = "流水号")
    private String requestId;

    @Schema(description = "方案ID")
    private Long projectId;

    @Schema(description = "任务编号")
    private String taskNo;
}
