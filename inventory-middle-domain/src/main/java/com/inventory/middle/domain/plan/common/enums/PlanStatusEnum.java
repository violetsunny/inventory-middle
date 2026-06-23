package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划生效枚举
 * @date 2021/10/2 15:11
 */
@Getter
public enum PlanStatusEnum {

    ON(1, "启用"),
    OFF(0, "停用");

    PlanStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;

    private String desc;

    public static PlanStatusEnum getByCode(Integer value) {
        for (PlanStatusEnum e : PlanStatusEnum.values()) {
            if (e.getCode().equals(value)) {
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
