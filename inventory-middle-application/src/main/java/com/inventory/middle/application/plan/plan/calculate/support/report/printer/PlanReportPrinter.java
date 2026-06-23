package com.inventory.middle.application.plan.plan.calculate.support.report.printer;

import com.inventory.middle.application.plan.plan.calculate.bo.report.MaterialPlanReportBO;

/**
 * 计划报表打印器
 *
 * @author Danny.Lee
 * @date 2021/10/14
 */
public interface PlanReportPrinter {

    /**
     * 打印计划报表
     *
     * @param planReport 计划报表
     * @return 计划报表打印内容
     */
    String print(MaterialPlanReportBO planReport);
}
