package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 物料计划详情命名空间
 *
 * @author Danny.Lee
 * @date 2021/11/4
 */
@Getter
public enum MaterialPlanDetailNsExtKeyEnum {

    /**
     * 指标数据
     */
    INDICATORS("indicators", "指标数据"),

    /**
     * 指标扩展数据
     */
    INDICATOR_EXT_ATTRS("indicatorExtAttrs", "指标扩展数据"),

    /**
     * 业务扩展数据
     */
    BUSINESS_EXT_ATTRS("businessExtAttrs", "业务扩展数据"),
    ;

    private final String code;

    private final String desc;

    MaterialPlanDetailNsExtKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
