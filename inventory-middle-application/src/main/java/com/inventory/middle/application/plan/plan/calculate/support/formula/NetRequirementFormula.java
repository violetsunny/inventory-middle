package com.inventory.middle.application.plan.plan.calculate.support.formula;

import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * 净需求量<br/>
 * 净需求量 = 安全库存 - 上期末预计可用库存 - 本期计划接收量 + 毛需求量
 * 净需求量 = （本期毛需求+历史逾期计划出）-（上期末预计库存+本期计划接收+历史逾期计划接收）+安全库存（取决于是否选择）？？
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.NET_REQUIREMENT)
public class NetRequirementFormula implements Formula {

    private final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);
    private final BigDecimal TWO = BigDecimal.valueOf(2);

    @Resource
    private Formula planReceiveFormula;

    @Resource
    private Formula grossRequirementFormula;

    @Resource
    private Formula projectedAvailableBalanceFormula;

    @Override
    public Indicator apply(MaterialFactor factor) {
        Indicator indicator = Indicator.of();
        // 安全库存
        BigDecimal safetyStock = factor.getSafetyStock();
        indicator.addExtAttr("safetyStock", safetyStock);
        // 计划接收量
        BigDecimal planReceive = planReceiveFormula.apply(factor).value();
        indicator.addExtAttr(planReceiveFormula.indicatorCode(), planReceive);
        // 毛需求量
        BigDecimal grossRequirement = this.grossForParameterOptions(factor, grossRequirementFormula.apply(factor).value());
        indicator.addExtAttr(grossRequirementFormula.indicatorCode(), grossRequirement);
        // 上期期末可用库存
        BigDecimal previousPab = projectedAvailableBalanceFormula.apply(factor.previous()).value();
        indicator.addExtAttr(projectedAvailableBalanceFormula.indicatorCode(), previousPab);

        //净需求 =  安全库存 - 上期末预计可用库存 - 计划接收量 + 毛需求量
        return indicator.value(
                this.resetNetRequirement(safetyStock
                        .subtract(previousPab)
                        .subtract(planReceive)
                        .add(grossRequirement)));
    }

    /**
     * 净需求小于0时说明当前库存满足需求，净需求置为0
     *
     * @param netRequirement 净需求
     * @return 重置的净需求
     */
    private BigDecimal resetNetRequirement(BigDecimal netRequirement) {
        return netRequirement.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : netRequirement;
    }

    /**
     * 根据参数选项补充毛需求数量
     * <pre>
     *     1. 计划类型=生产,考虑参数是否启用成品率;毛需求=Math.ceil(毛需求*(成品率))<br/>
     *     2. 计划类型=采购/调拨,考虑参数是否启用损耗率;毛需求=Math.ceil(毛需求*(1+损耗率))<br/><br/>
     * </pre>
     *
     * @param factor           物料因子
     * @param grossRequirement 毛需求
     * @return 补充后的毛需求数量
     */
    private BigDecimal grossForParameterOptions(MaterialFactor factor, BigDecimal grossRequirement) {
        BigDecimal grossIncrease = grossRequirement;
        if (factor.getPlanType() == PlanMaterialParamPlanTypeEnum.PRODUCER) {
            if (factor.getEnableFinishedRate()) {
                BigDecimal finishedRate = Optional.ofNullable(factor.getFinishedRate()).orElse(BigDecimal.valueOf(10000));
                // 毛需求 = 毛需求*(1+(1-成品率))
                grossIncrease = grossIncrease.multiply(TWO.subtract(finishedRate.divide(TEN_THOUSAND, 4, ROUND_HALF_UP)))
                        .setScale(0, RoundingMode.CEILING);
            }
        } else {
            if (factor.getEnableLossRate()) {
                BigDecimal lossRate = Optional.ofNullable(factor.getLossRate()).orElse(BigDecimal.ZERO);
                // 毛需求 = 毛需求*(1+损耗率)
                grossIncrease = grossIncrease.multiply(lossRate.divide(TEN_THOUSAND, 4, ROUND_HALF_UP).add(BigDecimal.ONE))
                        .setScale(0, RoundingMode.CEILING);
            }
        }
        return grossIncrease;
    }
}
