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
 * 相关需求
 *
 * @author Danny.Lee
 */
@Component
@IndicatorSpec(Indicators.CORRELATED)
public class CorrelatedFormula implements Formula {

    @Resource
    private Formula mpsPlanInvestFormula;

    @Override
    public Indicator apply(MaterialFactor factor) {
        Indicator indicator = Indicator.of();

        // MPS计划投入量
        BigDecimal mpsPlanInvest = mpsPlanInvestFormula.apply(factor).value();
        indicator.addExtAttr(mpsPlanInvestFormula.indicatorCode(), mpsPlanInvest);

        BigDecimal amount = Optional.ofNullable(factor.getBomFactor())
                .map(value -> new BigDecimal(String.valueOf(value.getAmount())))
                .orElse(BigDecimal.ZERO);
        indicator.addExtAttr("consumptionAmount", amount.toString());

        return indicator.value(mpsPlanInvest.multiply(amount));
    }
}
