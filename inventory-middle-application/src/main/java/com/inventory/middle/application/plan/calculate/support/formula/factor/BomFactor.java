package com.inventory.middle.application.plan.calculate.support.formula.factor;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * 物料清单因子
 *
 * @author Danny.Lee
 * @date 2021/9/27
 */
@Data
public class BomFactor implements Factor {

    /**
     * 数量
     */
    private long amount;

    /**
     * 相关需求-MPS计划产出
     */
    private Map<LocalDate, BigDecimal> correlatedPlanProduces;

    /**
     * 相关需求-MPS计划投入
     */
    private Map<LocalDate, BigDecimal> correlatedPlanInvests;

    public BigDecimal correlatedPlanProduce(LocalDate retrieveDate) {
        return correlatedPlanProduces.getOrDefault(retrieveDate, BigDecimal.ZERO);
    }

    public BigDecimal correlatedPlanInvest(LocalDate retrieveDate) {
        return correlatedPlanInvests.getOrDefault(retrieveDate, BigDecimal.ZERO);
    }
}
