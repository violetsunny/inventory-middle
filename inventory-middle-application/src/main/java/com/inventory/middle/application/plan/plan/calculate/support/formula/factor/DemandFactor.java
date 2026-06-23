package com.inventory.middle.application.plan.plan.calculate.support.formula.factor;

import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * 需求因子
 *
 * @author Danny.Lee
 * @date 2021/9/27
 */
@Data
public class DemandFactor implements Factor {

    /**
     * 物料订单需求
     */
    private Map<LocalDate, BigDecimal> materialOrderDemands;

    /**
     * 物料预测需求
     */
    private Map<LocalDate, BigDecimal> materialPredictDemands;


    public BigDecimal orderDemand(LocalDate retrieveDate) {
        if (MapUtils.isEmpty(materialOrderDemands)) {
            return BigDecimal.ZERO;
        }
        return materialOrderDemands.getOrDefault(retrieveDate, BigDecimal.ZERO);
    }

    public BigDecimal predictDemand(LocalDate retrieveDate) {
        if (MapUtils.isEmpty(materialPredictDemands)) {
            return BigDecimal.ZERO;
        }
        return materialPredictDemands.getOrDefault(retrieveDate, BigDecimal.ZERO);
    }
}
