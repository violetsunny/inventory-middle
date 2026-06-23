package com.inventory.middle.application.plan.plan.calculate.support.formula;

import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * 计划投入量
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.PLAN_INVEST)
public class PlanInvestFormula implements Formula {

    @Resource
    private Formula planProduceFormula;

    @Override
    public Indicator apply(MaterialFactor factor) {
        Indicator indicator = Indicator.of();
        final int vendorLeadTime = Optional.ofNullable(factor.getVendorLeadTime()).orElse(0);
        if(factor.isBeforeStartDate()){
            BigDecimal planInvest = BigDecimal.ZERO;
            MaterialFactor next = factor.now();
            for (int i = 0; i < vendorLeadTime; i++) {
                next = next.next();
                planInvest = planInvest.add(planProduceFormula.apply(next).value());
            }
            return indicator.value(planInvest);
        }

        MaterialFactor nextDaysFactor = factor.nextDays(vendorLeadTime);
        indicator.addExtAttr("planProduceDate", nextDaysFactor.getPlanDate());

        return indicator.value(planProduceFormula.apply(nextDaysFactor).value());
    }
}
