package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "计划任务通知参数")
public class ProjectTaskNotifyReqDTO implements Serializable {

    @Schema(description = "流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestId;

    @Schema(description = "返回码")
    private Long calResultCode;

    @Schema(description = "优化指标结果")
    private String optTarget;

    @Schema(description = "算法计算请求ID")
    private String reRequestId;

    @Schema(description = "算法计算任务编号")
    private String reTaskNo;

    @Schema(description = "原始返回报文")
    private String originalBody;

    @Schema(description = "临时数据")
    private String tempData;
}
