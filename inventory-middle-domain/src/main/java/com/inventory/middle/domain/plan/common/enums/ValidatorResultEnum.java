package com.inventory.middle.domain.plan.common.enums;

/**
 * 校验结果枚举
 * <p>
 * 迁移自 com.enn.plan.management.common.enums.ValidatorResultEnum
 * </p>
 *
 * @author migrated from scm-plan-management
 */
public enum ValidatorResultEnum {

    DEMAND_PLAN_STATUS_IN_USE("V_0001", "请先停用该需求计划关联的计划方案"),

    DEMAND_PLAN_MATERIAL_DUPLICATE("V_0002", "该需求计划中存在物料已在其他计划中启用"),

    DEMAND_PLAN_STATUS_VALID("V_0003", "需求计划已启用/停用，请勿重复启用/停用"),

    DEMAND_PLAN_NO_MATERIAL("V_0005", "请维护物料后再启用"),

    FAILED("1", "失败"),

    ;

    ValidatorResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private String code;

    private String desc;
}
