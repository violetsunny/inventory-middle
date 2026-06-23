package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 物料场景类型枚举
 *
 * @author caosheng
 * @date 2021/12/9
 */
@Getter
public enum MaterialSceneTypeEnum {

    /**
     * 计划方案-指定物料场景
     */
    PLAN_MATERIAL_TYPE(1, "计划方案-指定物料场景"),

    /**
     * 独立需求物料场景
     */
    DEMAND_INDEPENDENT_TYPE(2, "独立需求物料场景"),
    ;

    private final Integer code;

    private final String desc;

    MaterialSceneTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
