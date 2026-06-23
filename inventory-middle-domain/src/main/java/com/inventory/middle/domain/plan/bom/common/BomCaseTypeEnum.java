package com.inventory.middle.domain.plan.bom.common;

/**
 * BOM清单类型枚举
 */
public enum BomCaseTypeEnum {

    HOMEMADE(0, "自制类物料BOM"),
    ;

    BomCaseTypeEnum(Integer code, String desc) {
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
