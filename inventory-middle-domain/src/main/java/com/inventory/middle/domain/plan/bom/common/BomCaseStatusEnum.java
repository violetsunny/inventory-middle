package com.inventory.middle.domain.plan.bom.common;

/**
 * BOM单状态枚举
 */
public enum BomCaseStatusEnum {

    ON(1, "启用"),

    OFF(0, "停用"),
    ;

    BomCaseStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
