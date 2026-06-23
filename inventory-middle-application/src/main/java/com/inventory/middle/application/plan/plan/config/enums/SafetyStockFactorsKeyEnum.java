package com.inventory.middle.application.plan.plan.config.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 安全库存公式参数键
 *
 * @author Danny.Lee
 * @date 2022/5/9
 */
@Getter
public enum SafetyStockFactorsKeyEnum {

    /**
     * 安全系数
     */
    SAFETY_COEFFICIENT("safetyCoefficient", "安全系数"),

    /**
     * 保障间隔
     */
    GUARANTEE_INTERVAL("guaranteeInterval", "保障间隔"),

    /**
     * 保障间隔内的需求标准差
     */
    DEMAND_STD("demandStd", "保障间隔内的需求标准差"),
    ;

    SafetyStockFactorsKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;

    private final String desc;

    public static SafetyStockFactorsKeyEnum of(String code) {
        return Arrays.stream(SafetyStockFactorsKeyEnum.values())
                .filter(key -> Objects.equals(key.getCode(), code))
                .findFirst()
                .orElse(null);
    }
}
