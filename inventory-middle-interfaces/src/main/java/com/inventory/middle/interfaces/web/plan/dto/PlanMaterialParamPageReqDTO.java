package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询查询物料计划参数 入参
 * @date 2021/9/30 19:15
 */
@Data
public class PlanMaterialParamPageReqDTO implements Serializable {

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "ERP编码", required = true)
    private String externalMaterialCode;

    @Schema(description = "逻辑仓编码", required = true)
    private String logicalPlantNo;

    @Schema(description = "操作人")
    private String operatorName;

    @Schema(description = "页码:从1起始", required = true)
    private Integer pageNum;

    @Schema(description = "分页大小", required = true)
    private Integer pageSize;
}