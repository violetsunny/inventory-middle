package com.inventory.middle.application.plan.plan.config.enums;

import lombok.Getter;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案 计算参数 是否启用损耗率枚举
 * @date 2021/10/12 14:10
 */
@Getter
public enum EnableLossRateEnum {

    /**
     * 启用
     */
    ON(1, "启用"),

    /**
     * 失效
     */
    OFF(0, "不启用");


    EnableLossRateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;

    private final String desc;

    public static EnableLossRateEnum getByCode(Integer value) {
        for (EnableLossRateEnum e : EnableLossRateEnum.values()) {
            if (e.getCode().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
