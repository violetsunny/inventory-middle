package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询物料计划参数
 * @date 2021/9/30 19:27
 */
@Data
public class PlanMaterialParamPageResVO implements Serializable {

    private static final long serialVersionUID = 329901712650765278L;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "外部物料编码")
    private String externalMaterialCode;

    @Schema(description = "逻辑仓名称")
    private String logicalPlantName;

    @Schema(description = "逻辑仓NO")
    private String logicalPlantNo;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "操作人")
    private String operatorName;

    @Schema(description = "物料描述")
    private String materialDesc;

    @Schema(description = "调拨源头仓")
    private String transferLogicalPlantNo;

}