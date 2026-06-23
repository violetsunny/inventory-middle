package com.inventory.middle.application.plan.plan.config.enums;

import lombok.Getter;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案 计算参数 是否启用安全库存枚举
 * @date 2021/10/12 14:10
 */
@Getter
public enum EnableSafetyStockEnum {

    /**
     * 启用
     */
    ON(1, "启用"),

    /**
     * 失效
     */
    OFF(0, "不启用");


    EnableSafetyStockEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;

    private final String desc;

    public static EnableSafetyStockEnum getByCode(Integer value) {
        for (EnableSafetyStockEnum e : EnableSafetyStockEnum.values()) {
            if (e.getCode().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
