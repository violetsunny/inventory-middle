package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划方案 计算参数 计划量策略枚举
 *
 * @author peisheng.wang
 * @date 2021/10/12
 */
@Getter
@Deprecated
public enum PlanQuantityStrategyEnum {

    DEFINITE_QUANTITY("PQS0", "定量"),
    INDEFINITE_QUANTITY("PQS1", "不定量"),
    ;

    private final String code;

    private final String desc;

    PlanQuantityStrategyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PlanQuantityStrategyEnum getByCode(String code) {
        for (PlanQuantityStrategyEnum e : PlanQuantityStrategyEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
