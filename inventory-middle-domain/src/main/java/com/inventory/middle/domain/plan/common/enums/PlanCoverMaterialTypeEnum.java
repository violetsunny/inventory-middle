package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划物料的涵盖类型
 *
 * @author peisheng.wang
 * @date 2021/10/2
 */
@Getter
public enum PlanCoverMaterialTypeEnum {

    /**
     * 指定逻辑仓下所有物料
     */
    ALL(0, "所有物料"),
    /**
     * 指定逻辑仓下部分物料
     */
    APPOINT(1, "指定物料");

    private final Integer code;

    private final String desc;

    PlanCoverMaterialTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PlanCoverMaterialTypeEnum getByCode(Integer value) {
        for (PlanCoverMaterialTypeEnum e : PlanCoverMaterialTypeEnum.values()) {
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
