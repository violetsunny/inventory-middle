package com.inventory.middle.application.plan.calculate.support.formula;

import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

/**
 * 合同量/订单量
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.ORDER)
public class OrderFormula implements Formula {

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        return Indicator.of(materialFactor.orderDemand());
    }
}
