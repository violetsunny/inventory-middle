package com.inventory.middle.application.plan.plan.config.enums;

import lombok.Getter;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案 计算参数key枚举
 * @date 2021/10/13 10:54
 */
@Getter
public enum CalculateParamsKeyEnum {

    /**
     * 订货策略
     */
    @Deprecated
    ORDER_STRATEGY("orderStrategy", "订货策略key"),
    /**
     * 计划量策略
     */
    @Deprecated
    PLAN_QUANTITY_STRATEGY("planQuantityStrategy", "计划量策略key"),
    /**
     * 不定量策略
     */
    @Deprecated
    INDEFINITE_QUANTITY_STRATEGY("indefiniteQuantityStrategy", "不定量策略key"),

    /**
     * 订货计算规则
     */
    ORDER_CAL_RULE("orderCalRule", "订货计算规则key"),

    /**
     * 订货计算间隔
     */
    ORDER_CAL_INTERVAL("orderCalInterval", "订货计算间隔key"),

    /**
     * 订货量类型
     */
    ORDER_PRODUCE_TYPE("orderProduceType", "订货量类型key"),

    /**
     * 启用损耗率
     */
    ENABLE_LOSS_RATE("enableLossRate", "是否启用损耗率key"),
    /**
     * 启用成品率
     */
    ENABLE_FINISHED_RATE("enableFinishedRate", "是否启用成品率key"),
    /**
     * 启用安全库存
     */
    ENABLE_SAFETY_STOCK("enableSafetyStock", "是否启动安全库存"),
    ;

    CalculateParamsKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;

    private final String desc;

    public static CalculateParamsKeyEnum getByCode(String code) {
        for (CalculateParamsKeyEnum e : CalculateParamsKeyEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}