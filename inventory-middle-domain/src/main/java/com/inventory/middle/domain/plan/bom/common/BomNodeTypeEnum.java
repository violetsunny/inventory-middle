package com.inventory.middle.domain.plan.bom.common;

/**
 * BOM节点类型枚举
 */
public enum BomNodeTypeEnum {

    PARENT(0, "母件"),

    CHILD(1, "子件"),
    ;

    BomNodeTypeEnum(Integer code, String desc) {
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

    public static BomNodeTypeEnum getByCode(Integer value) {
        for (BomNodeTypeEnum e : BomNodeTypeEnum.values()) {
            if (e.getCode().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
