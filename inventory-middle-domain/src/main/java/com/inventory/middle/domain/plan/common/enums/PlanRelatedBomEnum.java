package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划方案是否关联BOM枚举
 *
 * @author caosheng
 */
@Getter
public enum PlanRelatedBomEnum {

    NOT_RELATED_BOM(0, "否"),
    RELATED_BOM(1, "是"),
    ;

    PlanRelatedBomEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public static PlanRelatedBomEnum getByCode(Integer value) {
        for (PlanRelatedBomEnum e : PlanRelatedBomEnum.values()) {
            if (e.getCode().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public static PlanRelatedBomEnum getByDesc(String desc) {
        for (PlanRelatedBomEnum e : PlanRelatedBomEnum.values()) {
            if (e.getDesc().equals(desc)) {
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

    public static boolean enableBom(Integer value) {
        return RELATED_BOM.getCode().equals(value);
    }
}
