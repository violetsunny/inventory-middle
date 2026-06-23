package com.inventory.middle.application.plan.plan.calculate.bo.report;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 物料计划报表期初指标
 *
 * @author Danny.Lee
 * @date 2021/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportInitIndicatorBO extends BaseBo {

    private static final long serialVersionUID = -6927410780172713994L;

    private Map<String, BigDecimal> initIndicators;

    public void putInitIndicator(String indicatorCode, BigDecimal indicatorValue) {
        if (null == initIndicators) {
            initIndicators = Maps.newHashMap();
        }
        initIndicators.put(indicatorCode, indicatorValue);
    }

    @Override
    public String toLog() {
        return this.toString();
    }
}
