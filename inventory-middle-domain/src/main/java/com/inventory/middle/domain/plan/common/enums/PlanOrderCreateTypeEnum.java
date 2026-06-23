package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划订单创建类型枚举
 *
 * @author peisheng.wang
 */
@Getter
public enum PlanOrderCreateTypeEnum {

    SYSTEM(0, "系统"),
    MANUAL(1, "手工"),
    ;

    PlanOrderCreateTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public static PlanOrderCreateTypeEnum getByCode(Integer code) {
        for (PlanOrderCreateTypeEnum e : PlanOrderCreateTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
