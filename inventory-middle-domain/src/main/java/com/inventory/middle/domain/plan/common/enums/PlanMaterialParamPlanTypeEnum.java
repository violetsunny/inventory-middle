package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 物料计划参数-计划类型枚举
 *
 * @author peisheng.wang
 */
@Getter
public enum PlanMaterialParamPlanTypeEnum {

    PURCHASE(0, "采购"),
    TRANSFER(1, "调拨"),
    PRODUCER(2, "生产"),
    ;

    PlanMaterialParamPlanTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public static PlanMaterialParamPlanTypeEnum getByCode(Integer value) {
        for (PlanMaterialParamPlanTypeEnum e : PlanMaterialParamPlanTypeEnum.values()) {
            if (e.getCode().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public static boolean isTransfer(Integer code) {
        return TRANSFER.code.equals(code);
    }

    public static boolean isProducer(Integer code) {
        return PRODUCER.code.equals(code);
    }

    public static PlanMaterialParamPlanTypeEnum getByDesc(String desc) {
        for (PlanMaterialParamPlanTypeEnum e : PlanMaterialParamPlanTypeEnum.values()) {
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
}
