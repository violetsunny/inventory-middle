package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 导入物料计划参数失败详情
 * @date 2021/9/26 14:10
 */
@Data
public class PlanMaterialParamImportDetailVO implements Serializable {

    private static final long serialVersionUID = 8469641367496124457L;

    @Schema(description = "表格索引")
    private Integer index;

    @Schema(description = "物料名称")
    private String materialCode;

    @Schema(description = "失败原因")
    private String failedReason;
}