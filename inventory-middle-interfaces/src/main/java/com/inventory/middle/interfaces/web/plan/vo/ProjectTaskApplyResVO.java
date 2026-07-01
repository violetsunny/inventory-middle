package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "计划任务申请结果")
public class ProjectTaskApplyResVO implements Serializable {

    @Schema(description = "流水号")
    private String requestId;

    @Schema(description = "任务编号")
    private String taskNo;
}
