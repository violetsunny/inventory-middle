package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 计划产出类型枚举
 *
 * @author Danny.Lee
 * @date 2021/10/10
 */
@Getter
public enum PlanProduceEnum {

    /**
     * 定量-批次
     */
    FIX_BATCH(0, "定量"),
    /**
     * 不定量-净需求
     */
    NET_REQUIREMENT(1, "根据净需求"),
    /**
     * 不定量-目标库存差额
     */
    TARGET_BALANCE(2, "根据目标差额"),
    /**
     * 不定量-补货点净需求规则
     */
    NET_REQUIREMENT_EXT(3, "根据净需求"),
    ;

    private final Integer code;

    private final String desc;

    PlanProduceEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PlanProduceEnum of(Integer code) {
        return Arrays.stream(PlanProduceEnum.values())
                .filter(item -> Objects.equals(item.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    public static PlanProduceEnum of(OrderStrategyEnum orderStrategy,
                                     PlanQuantityStrategyEnum planQuantityStrategy,
                                     IndefiniteQuantityStrategyEnum indefiniteQuantityStrategy) {
        if (planQuantityStrategy == PlanQuantityStrategyEnum.DEFINITE_QUANTITY) {
            return FIX_BATCH;
        } else {
            if (IndefiniteQuantityStrategyEnum.NET_DEMAND == indefiniteQuantityStrategy) {
                return OrderStrategyEnum.isNetDemand(orderStrategy.getCode()) ? NET_REQUIREMENT : NET_REQUIREMENT_EXT;
            }
            return TARGET_BALANCE;
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
