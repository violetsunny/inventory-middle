package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物料计划报表
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划报表")
public class MaterialPlanReportVO implements Serializable {

    private static final long serialVersionUID = 6848854739359843725L;

    /**
     * 标题
     */
    @Schema(description = "物料计划标题")
    private MaterialPlanReportTitleVO title;

    /**
     * 参数
     */
    @Schema(description = "物料计划参数")
    private MaterialPlanReportParamVO param;

    /**
     * 时段
     */
    @Schema(description = "物料计划时段")
    private MaterialPlanReportPeriodVO period;

    /**
     * 期初指标
     */
    @Schema(description = "物料计划期初指标")
    private MaterialPlanReportInitIndicatorVO initIndicator;

    /**
     * 指标
     */
    @Schema(description = "物料计划指标")
    private List<MaterialPlanReportIndicatorVO> indicators;
}
