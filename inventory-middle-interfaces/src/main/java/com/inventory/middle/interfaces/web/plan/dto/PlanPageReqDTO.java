package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询入参
 * @date 2021/10/7 15:35
 */
@Data
public class PlanPageReqDTO implements Serializable {

    private static final long serialVersionUID = 1147834403720817324L;

    @Schema(description = "计划方案号")
    private String planCode;

    @Schema(description = "方案描述")
    private String planDesc;

    @Schema(description = "执行类型")
    private Integer planType;

    @Schema(description = "操作人")
    private String operatorName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "页码:从1起始", required = true)
    private Integer pageNum;

    @Schema(description = "分页大小", required = true)
    private Integer pageSize;
}