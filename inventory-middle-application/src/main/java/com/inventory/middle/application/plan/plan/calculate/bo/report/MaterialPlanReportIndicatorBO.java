package com.inventory.middle.application.plan.plan.calculate.bo.report;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物料计划报表指标
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportIndicatorBO extends BaseBo {

    private static final long serialVersionUID = 1107989167024602968L;

    private String indicatorCode;

    private String indicatorName;

    private List<BigDecimal> indicators;

    public void appendIndicator(BigDecimal indicator) {
        if (CollectionUtils.isEmpty(indicators)) {
            this.indicators = Lists.newArrayList();
        }
        this.indicators.add(indicator);
    }

    @Override
    public String toLog() {
        return this.toString();
    }
}
