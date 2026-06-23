package com.inventory.middle.application.plan.calculate.support.formula;

import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 可供销售量
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.ATP)
public class AvailableToPromiseFormula implements Formula {

    @Resource
    private Formula planProduceFormula;

    @Resource
    private Formula planReceiveFormula;

    @Resource
    private Formula projectedAvailableBalanceFormula;

    @Override
    public Indicator apply(MaterialFactor factor) {
        Indicator indicator = Indicator.of();
        // 当因子考虑BOM支持时，需要扣减相关需求汇总
        // 当本期计划出>0时，计划首日ATP =  本期计划产出 + 本期计划接收 - 订单汇总
        // 当本期计划出<=0时，计划首日ATP =  本期计划产出 + 本期计划接收 - 订单汇总 + 上期预计可用库存
        // 当本期计划出>0时，非计划首日ATP =  本期计划产出 + 本期计划接收 - 订单汇总
        // 当本期计划出<=0时，非计划首日ATP = 上期ATP

        // 本期计划产出
        BigDecimal planProduce = planProduceFormula.apply(factor).value();
        indicator.addExtAttr(planProduceFormula.indicatorCode(), planProduce);
        if (!factor.isStartDate() && planProduce.compareTo(BigDecimal.ZERO) <= 0) {
            // 非计划首日的计划产出量为0时，使用上期的ATP指标
            MaterialFactor previous = factor.previous();
            AvailableToPromiseFormula formula = (AvailableToPromiseFormula)AopContext.currentProxy();
            BigDecimal previousAtp = formula.apply(previous).value();
            indicator.addExtAttr("previousAtp", previousAtp);
            return indicator.value(formula.apply(previous).value());
        }

        // 本期计划接收
        BigDecimal planReceive = planReceiveFormula.apply(factor).value();
        indicator.addExtAttr(planReceiveFormula.indicatorCode(), planReceive);

        // 本次至下一次出现计划产出量之前各时段订单量之和
        final BigDecimal totalOrder = this.collectOrderUntilNextProduce(indicator, factor);

        BigDecimal previousPab = BigDecimal.ZERO;
        if (factor.isStartDate() && planProduce.compareTo(BigDecimal.ZERO) <= 0) {
            MaterialFactor previous = factor.previous();
            previousPab = projectedAvailableBalanceFormula.apply(previous).value();
            indicator.addExtAttr("previousPab", previousPab);
        }
        return indicator.value(planProduce.add(planReceive).subtract(totalOrder).add(previousPab));
    }

    private BigDecimal collectOrderUntilNextProduce(Indicator indicator, MaterialFactor factor) {
        MaterialFactor next = factor;
        BigDecimal totalOrder = factor.orderDemand();
        for (LocalDate date = factor.getPlanDate().plusDays(1);
             !date.isAfter(factor.getPlanEndDate());
             date = date.plusDays(1)) {
            next = next.next();
            BigDecimal nextPlanProduce = planProduceFormula.apply(next).value();
            if (nextPlanProduce.compareTo(BigDecimal.ZERO) > 0) {
                break;
            }
            totalOrder = totalOrder.add(next.orderDemand());

        }
        indicator.addExtAttr("nextProduceDate", next.getPlanDate());
        indicator.addExtAttr("totalOrder", totalOrder);
        return totalOrder;
    }
}
