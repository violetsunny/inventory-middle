package com.inventory.middle.domain.plan.common.enums;

import java.util.Arrays;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 21:02
 */
public enum DemandPlanMaterialDetailTypeEnum {

    PLAN(1,"预测需求"),
    ORDER(2,"订单需求"),
    REVERSE(3,"冲销")
    ;


    DemandPlanMaterialDetailTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    private int code;

    private String desc;

    public static DemandPlanMaterialDetailTypeEnum of(int code) {
        return Arrays.stream(DemandPlanMaterialDetailTypeEnum.values())
                .filter(item -> item.getCode() == code)
                .findFirst()
                .orElse(null);
    }

}
