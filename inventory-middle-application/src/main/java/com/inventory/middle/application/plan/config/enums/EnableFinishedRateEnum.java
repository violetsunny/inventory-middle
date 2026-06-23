package com.inventory.middle.application.plan.config.enums;

import lombok.Getter;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案 计算参数 是否启用成品率枚举
 * @date 2021/10/12 14:10
 */
@Getter
public enum EnableFinishedRateEnum {
    /**
     * 启用
     */
    ON(1, "启用"),
    /**
     * 失效
     */
    OFF(0, "不启用");


    EnableFinishedRateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;

    private final String desc;

    public static EnableFinishedRateEnum getByCode(Integer value) {
        for (EnableFinishedRateEnum e : EnableFinishedRateEnum.values()) {
            if (e.getCode().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
