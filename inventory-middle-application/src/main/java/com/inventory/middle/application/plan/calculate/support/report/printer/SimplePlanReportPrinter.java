package com.inventory.middle.application.plan.calculate.support.report.printer;

import com.inventory.middle.domain.plan.common.enums.PlanProduceEnum;
import com.inventory.middle.application.plan.calculate.bo.report.*;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 基本计划报表打印器
 *
 * @author Danny.Lee
 * @date 2021/10/14
 */
@Component
public class SimplePlanReportPrinter implements PlanReportPrinter {

    @Override
    public String print(MaterialPlanReportBO planReport) {
        StringBuilder report = new StringBuilder();
        report.append(printTitle(planReport.getTitle()))
                .append(printParam(planReport.getParam()))
                .append(printPeriod(planReport.getPeriod()))
                .append(printInitIndicators(planReport.getInitIndicator()))
                .append(printIndicators(planReport.getIndicators()));
        return report.toString();
    }

    private String printTitle(MaterialPlanReportTitleBO planReportTitle) {
        StringBuilder title = new StringBuilder("Title:\n");
        title.append("物料号:").append(planReportTitle.getMaterialCode()).append("\n");
        title.append("物料名:").append(planReportTitle.getMaterialDesc()).append("\n");
        title.append("计划日期:").append(planReportTitle.getCreateDate()).append("\n");
        title.append("计划员:").append(planReportTitle.getOperator()).append("\n");
        return title.toString();
    }

    private String printParam(MaterialPlanReportParamBO planReportParam) {
        StringBuilder param = new StringBuilder("Param:\n");
        param.append("提前期:").append(planReportParam.getVendorLeadTime()).append("\n");
        param.append("现有库存:").append(planReportParam.getCurrentStock()).append("\n");
        param.append("安全库存:").append(planReportParam.getSafetyStock()).append("\n");
        param.append("订货批量:").append(planReportParam.getOrderQuantity()).append("\n");
        param.append("补货规则:").append(
                Optional.ofNullable(PlanProduceEnum.of(planReportParam.getProduceType()))
                        .map(PlanProduceEnum::getDesc)
                        .orElse(null)).append("\n");
        param.append("需求时区:").append(planReportParam.getDemandTimeFence()).append("\n");
        param.append("计划时区:").append(planReportParam.getPlanningTimeFence()).append("\n");
        return param.toString();
    }

    private String printPeriod(MaterialPlanReportPeriodBO planReportPeriod) {
        StringBuilder period = new StringBuilder("Period:\n");
        int i = 1;
        for (LocalDate localDate : planReportPeriod.getPeriodStartDates()) {
            period.append(i++).append("-").append(localDate).append("|");
        }
        period.append("\n");
        return period.toString();
    }

    private String printInitIndicators(MaterialPlanReportInitIndicatorBO initIndicator) {
        StringBuilder stringBuilder = new StringBuilder("InitIndicator:\n");
        if (MapUtils.isNotEmpty(initIndicator.getInitIndicators())) {
            for (Map.Entry<String, BigDecimal> indicator : initIndicator.getInitIndicators().entrySet()) {
                stringBuilder.append(indicator.getKey()).append(":").append(indicator.getValue()).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private String printIndicators(List<MaterialPlanReportIndicatorBO> indicators) {
        StringBuilder stringBuilder = new StringBuilder("Indicators:\n");
        for (MaterialPlanReportIndicatorBO indicator : indicators) {
            stringBuilder.append(indicator.getIndicatorName()).append(":");
            for (BigDecimal value : indicator.getIndicators()) {
                stringBuilder.append(value).append("|");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
