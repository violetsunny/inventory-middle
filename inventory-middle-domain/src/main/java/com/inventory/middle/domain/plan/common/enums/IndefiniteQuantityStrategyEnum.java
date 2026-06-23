package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划方案 计算参数 不定量策略枚举
 *
 * @author peisheng.wang
 * @date 2021/10/12
 */
@Getter
@Deprecated
public enum IndefiniteQuantityStrategyEnum {

    NET_DEMAND("IQS0", "净需求规则"),
    GOAL_DIFFERENCE("IQS1", "目标差额规则"),
    ;

    private final String code;

    private final String desc;

    IndefiniteQuantityStrategyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IndefiniteQuantityStrategyEnum getByCode(String code) {
        for (IndefiniteQuantityStrategyEnum e : IndefiniteQuantityStrategyEnum.values()) {
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
