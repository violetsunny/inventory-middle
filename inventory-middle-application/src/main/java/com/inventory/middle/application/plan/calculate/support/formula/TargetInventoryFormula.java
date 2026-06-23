package com.inventory.middle.application.plan.calculate.support.formula;

import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * 目标库存量<br/>
 * <pre>
 *     目标库存=提前期内需求量+安全库存+物料补货周期内需求量
 *     提前期内需求量=Σ(i=1..物料提前期)毛需求{@link GrossRequirementFormula}
 *     物料补货周期内需求量=Σ(i=1..物料补货周期)毛需求{@link GrossRequirementFormula}
 * </pre>
 *
 * @author Danny.Lee
 * @date 2021/10/8
 */
@Component
@IndicatorSpec(Indicators.TARGET_INVENTORY)
public class TargetInventoryFormula implements Formula {

    @Resource
    private Formula grossRequirementFormula;

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        Indicator indicator = Indicator.of();
        final int vendorLeadTime = Optional.ofNullable(materialFactor.getVendorLeadTime()).orElse(0);
        final int orderCycleTime = Optional.ofNullable(materialFactor.getOrderCycleTime()).orElse(0);
        final int threshold = Math.max(vendorLeadTime, orderCycleTime);

        // 安全库存
        BigDecimal safetyStock = this.querySafetyStock(materialFactor);
        indicator.addExtAttr("safetyStock", safetyStock);

        MaterialFactor factorForNext = materialFactor;
        for (int i = 1; i <= threshold; i++) {
            factorForNext = factorForNext.next();
            final BigDecimal nextGrossRequirement = grossRequirementFormula.apply(factorForNext).value();
            if (i <= vendorLeadTime) {
                safetyStock = safetyStock.add(nextGrossRequirement);
            }
            if (i <= orderCycleTime) {
                safetyStock = safetyStock.add(nextGrossRequirement);
            }
        }
        return indicator.value(safetyStock);
    }

    private BigDecimal querySafetyStock(MaterialFactor materialFactor) {
        // 未启用安全库存
        if (!materialFactor.getEnableSafetyStock()) {
            return BigDecimal.ZERO;
        }
        return Optional.ofNullable(materialFactor.getSafetyStock()).orElse(BigDecimal.ZERO);
    }
}
