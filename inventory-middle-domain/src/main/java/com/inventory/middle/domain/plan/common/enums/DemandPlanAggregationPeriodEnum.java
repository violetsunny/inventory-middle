package com.inventory.middle.domain.plan.common.enums;

import java.util.Arrays;

/**
 * @description:
 * @author:xiaokang
 * @date:2021/9/29 13:51
 */
public enum DemandPlanAggregationPeriodEnum {
    DAY(1, "天","D"),
    WEEK(2, "周","W"),
    MONTH(3, "月","M"),

    ;
    private int code;

    private String desc;

    private String prefix;

    DemandPlanAggregationPeriodEnum(int code, String desc,String prefix) {
        this.code = code;
        this.desc = desc;
        this.prefix = prefix;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrefix() {
        return prefix;
    }

    public static DemandPlanAggregationPeriodEnum of(int code) {
        return Arrays.stream(DemandPlanAggregationPeriodEnum.values())
                .filter(item -> item.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}
