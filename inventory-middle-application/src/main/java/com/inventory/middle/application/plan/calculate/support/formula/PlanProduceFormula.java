package com.inventory.middle.application.plan.calculate.support.formula;

import com.inventory.middle.domain.plan.common.enums.PlanProduceEnum;
import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 计划产出量<br/>
 * 计划产出量依赖计划方案的计划量配置
 * <pre>
 *     1. 当计划产出策略=固定批量，产出量=满足净需求的最小倍数
 *     2. 当计划产出策略=净需求，产出量=净需求
 *     3. 当计划产出策略=目标库存差额，产出量=
 *     目标库存{@link TargetInventoryFormula}-
 *     可用库存{@link AvailableInventoryFormula}
 * </pre>
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.PLAN_PRODUCE)
public class PlanProduceFormula implements Formula {

    @Resource
    private Formula netRequirementFormula;

    @Resource
    private Formula targetInventoryFormula;

    @Resource
    private Formula availableInventoryFormula;

    @Resource
    private Formula replenishmentPointFormula;

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        Indicator indicator = Indicator.of().addExtAttr("productType", materialFactor.getProduceType().getCode());
        // 定量批次
        if (materialFactor.getProduceType() == PlanProduceEnum.FIX_BATCH) {
            BigDecimal netRequirement = netRequirementFormula.apply(materialFactor).value();
            indicator.addExtAttr(netRequirementFormula.indicatorCode(), netRequirement);
            if (netRequirement.compareTo(BigDecimal.ZERO) == 0) {
                return indicator.value(BigDecimal.ZERO);
            }
            BigDecimal batchSize = new BigDecimal(materialFactor.getOrderQuantity());
            indicator.addExtAttr("batchSize", batchSize);
            BigDecimal batch = netRequirement.divide(batchSize, 0, RoundingMode.CEILING);
            return indicator.value(batch.multiply(batchSize));
        }
        // 净需求
        if (materialFactor.getProduceType() == PlanProduceEnum.NET_REQUIREMENT) {
            return indicator.value(netRequirementFormula.apply(materialFactor).value());
        }
        // 补货点规则-净需求
        if (materialFactor.getProduceType() == PlanProduceEnum.NET_REQUIREMENT_EXT) {
            BigDecimal netRequirement = netRequirementFormula.apply(materialFactor).value();
            indicator.addExtAttr(netRequirementFormula.indicatorCode(), netRequirement);
            BigDecimal replenishmentPoint = replenishmentPointFormula.apply(materialFactor).value();
            indicator.addExtAttr(replenishmentPointFormula.indicatorCode(), replenishmentPoint);
            return indicator.value(netRequirement.add(replenishmentPoint));
        }
        // 目标库存差额
        if (materialFactor.getProduceType() == PlanProduceEnum.TARGET_BALANCE) {
            BigDecimal targetInventory = targetInventoryFormula.apply(materialFactor).value();
            indicator.addExtAttr(targetInventoryFormula.indicatorCode(), targetInventory);
            BigDecimal availableBalance = availableInventoryFormula.apply(materialFactor).value();
            indicator.addExtAttr(availableInventoryFormula.indicatorCode(), availableBalance);
            if (targetInventory.compareTo(availableBalance) <= 0) {
                return indicator.value(BigDecimal.ZERO);
            }
            return indicator.value(targetInventory.subtract(availableBalance));
        }
        return indicator.value(BigDecimal.ZERO);
    }


}
