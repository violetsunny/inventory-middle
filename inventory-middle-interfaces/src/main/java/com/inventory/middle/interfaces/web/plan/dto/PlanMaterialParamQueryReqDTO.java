package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询物料计划参数 入参
 * @date 2021/9/30 15:59
 */
@Data
public class PlanMaterialParamQueryReqDTO implements Serializable {


    private static final long serialVersionUID = 2897206792410340668L;

    @Schema(description = "物料编码", required = true)
    private String materialCode;

    @Schema(description = "外部物料编码", required = true)
    private String externalMaterialCode;

    @Schema(description = "逻辑仓编码", required = true)
    private String logicalPlantNo;
}