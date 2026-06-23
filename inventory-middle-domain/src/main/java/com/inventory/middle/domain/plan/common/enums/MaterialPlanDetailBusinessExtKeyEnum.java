package com.inventory.middle.domain.plan.common.enums;

/**
 * 物料计划详情业务扩展属性 Key 枚举
 * <p>
 * 迁移自 scm-plan-management 项目
 * </p>
 */
public enum MaterialPlanDetailBusinessExtKeyEnum {

    /**
     * 调拨供应商交货期（天）
     */
    TRANSFER_VENDOR_LEAD_TIME("transferVendorLeadTime", "调拨供应商交货期"),
    IGNORE_PLAN_ORDER("ignorePlanOrder", "忽略计划订单"),
    TRANSFER_PLANT("transferPlant", "调拨仓");

    private final String code;
    private final String desc;

    MaterialPlanDetailBusinessExtKeyEnum(String code, String desc) {
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
