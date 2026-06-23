package com.inventory.middle.application.plan.plan.calculate.support.formula;

import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

/**
 * 相关需求-MPS计划产出
 *
 * @author Danny.Lee
 */
@Component
@IndicatorSpec(Indicators.MPS_PLAN_PRODUCE)
public class MpsPlanProduceFormula implements Formula {

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        return Indicator.of(materialFactor.correlatedPlanProduce());
    }
}
