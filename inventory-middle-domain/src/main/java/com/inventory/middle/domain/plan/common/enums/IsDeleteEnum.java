package com.inventory.middle.domain.plan.common.enums;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/30 10:53
 */
public enum IsDeleteEnum {
    YES(1,"已删除"),
    NO(0,"未删除")
    ;

    IsDeleteEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private Integer value;

    private String desc;

    public Integer getValue() {
        return value;
    }
}
