package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划订单扩展信息key枚举
 *
 * @author peisheng.wang
 * @date 2021/12/15
 */
@Getter
public enum PlanOrderExtInfoKeyEnum {

    BOM_RELATION_DEMAND("bomRelationDemand", "BOM关联需求"),
    ;

    private final String code;

    private final String desc;

    PlanOrderExtInfoKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PlanOrderExtInfoKeyEnum getByCode(String code) {
        for (PlanOrderExtInfoKeyEnum e : PlanOrderExtInfoKeyEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
