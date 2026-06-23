package com.inventory.middle.application.plan.plan.calculate.bo.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.time.LocalDate;

/**
 * 物料计划报表标题
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialPlanReportTitleBO extends BaseBo {
    private static final long serialVersionUID = 1705120532724341371L;

    private String materialCode;

    private String materialDesc;

    private String planVersion;

    private LocalDate createDate;

    private String operator;

    @Override
    public String toLog() {
        return this.toString();
    }
}
