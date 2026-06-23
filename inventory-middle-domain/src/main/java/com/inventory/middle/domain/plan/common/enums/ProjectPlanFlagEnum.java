package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 项目订单类型
 *
 * @author xiaokang
 * @date 2022/05/11 09:57
 */
@Getter
public enum ProjectPlanFlagEnum {

    /**
     * 计划内
     */
    IN(1, "计划内"),

    /**
     * 计划外
     */
    OUT(0, "计划外"),
    ;

    private final Integer code;
    private final String desc;

    ProjectPlanFlagEnum(Integer code, String desc) {
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
