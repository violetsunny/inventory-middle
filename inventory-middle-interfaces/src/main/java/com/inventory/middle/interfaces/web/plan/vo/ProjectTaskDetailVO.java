package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "计划任务详情")
public class ProjectTaskDetailVO implements Serializable {

    @Schema(description = "流水号")
    private String requestId;

    @Schema(description = "任务编号")
    private String taskNo;

    @Schema(description = "请求状态")
    private Integer requestStatus;

    @Schema(description = "请求报文")
    private String requestBody;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "算法计算请求ID")
    private String reRequestId;

    @Schema(description = "算法计算任务编号")
    private String reTaskNo;

    @Schema(description = "原始返回报文")
    private String originalBody;

    @Schema(description = "临时数据")
    private String tempData;

    @Schema(description = "返回码")
    private Long calResultCode;

    @Schema(description = "优化指标结果")
    private String optTarget;

    @Schema(description = "算法计算创建时间")
    private LocalDateTime reCreateTime;
}
