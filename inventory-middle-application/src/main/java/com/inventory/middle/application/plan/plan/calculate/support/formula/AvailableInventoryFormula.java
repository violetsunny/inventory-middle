package com.inventory.middle.application.plan.plan.calculate.support.formula;

import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 可用库存量<br/>
 * 可用库存量 = 上期末预计可用库存量 + 计划接收量 - 毛需求量
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.AVAILABLE_INVENTORY)
public class AvailableInventoryFormula implements Formula {

    @Resource
    private Formula planReceiveFormula;

    @Resource
    private Formula grossRequirementFormula;

    @Resource
    private Formula projectedAvailableBalanceFormula;

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        Indicator indicator = Indicator.of();

        MaterialFactor materialLastPeriod = materialFactor.previous();

        // 上期可用库存
        BigDecimal previousPab = projectedAvailableBalanceFormula.apply(materialLastPeriod).value();
        indicator.addExtAttr("previousPab", previousPab);

        // 计划接收量
        BigDecimal planReceive = planReceiveFormula.apply(materialFactor).value();
        indicator.addExtAttr(planReceiveFormula.indicatorCode(), planReceive);

        // 毛需求量
        BigDecimal grossRequirement = grossRequirementFormula.apply(materialFactor).value();
        indicator.addExtAttr(grossRequirementFormula.indicatorCode(), grossRequirement);

        // 预计可用库存量 = 上期末预计可用库存量 + 计划接收量 - 毛需求量
        return indicator.value(previousPab
                .add(planReceive)
                .subtract(grossRequirement));
    }
}
