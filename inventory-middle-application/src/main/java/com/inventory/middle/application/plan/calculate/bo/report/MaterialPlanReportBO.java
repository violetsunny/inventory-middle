package com.inventory.middle.application.plan.calculate.bo.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.util.List;

/**
 * 物料计划报表
 *
 * @author Danny.Lee
 * @date 2021/10/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportBO extends BaseBo {

    private static final long serialVersionUID = 2888179328214655601L;

    /**
     * 标题
     */
    private MaterialPlanReportTitleBO title;

    /**
     * 参数
     */
    private MaterialPlanReportParamBO param;

    /**
     * 时段
     */
    private MaterialPlanReportPeriodBO period;

    /**
     * 期初指标
     */
    private MaterialPlanReportInitIndicatorBO initIndicator;

    /**
     * 指标
     */
    private List<MaterialPlanReportIndicatorBO> indicators;

    @Override
    public String toLog() {
        return this.toString();
    }
}
