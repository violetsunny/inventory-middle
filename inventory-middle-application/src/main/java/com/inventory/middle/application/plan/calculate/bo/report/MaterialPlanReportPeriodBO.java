package com.inventory.middle.application.plan.calculate.bo.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.time.LocalDate;
import java.util.List;

/**
 * 物料计划报表时段
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportPeriodBO extends BaseBo {

    private static final long serialVersionUID = 5371440007756661839L;

    private List<LocalDate> periodStartDates;

    @Override
    public String toLog() {
        return this.toString();
    }
}
