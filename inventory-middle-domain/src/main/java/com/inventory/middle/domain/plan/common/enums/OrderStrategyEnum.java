package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划方案 计算参数 订购策略枚举
 *
 * @author peisheng.wang
 * @date 2021/10/12
 */
@Getter
@Deprecated
public enum OrderStrategyEnum {

    REAL_TIME_NET_DEMAND("OS01", "实时净需求规则"),
    REAL_TIME_REPLENISH_POINT("OS02", "实时补货点规则"),
    CYCLE_TIME_NET_DEMAND("OS11", "周期净需求规则"),
    CYCLE_TIME_REPLENISH_POINT("OS12", "周期补货点规则"),
    ;

    private final String code;

    private final String desc;

    OrderStrategyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderStrategyEnum getByCode(String code) {
        for (OrderStrategyEnum e : OrderStrategyEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static boolean isCycleTime(String code) {
        OrderStrategyEnum orderStrategy = getByCode(code);
        return CYCLE_TIME_NET_DEMAND == orderStrategy || CYCLE_TIME_REPLENISH_POINT == orderStrategy;
    }

    public static boolean isNetDemand(String code) {
        OrderStrategyEnum orderStrategy = getByCode(code);
        return CYCLE_TIME_NET_DEMAND == orderStrategy || REAL_TIME_NET_DEMAND == orderStrategy;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
