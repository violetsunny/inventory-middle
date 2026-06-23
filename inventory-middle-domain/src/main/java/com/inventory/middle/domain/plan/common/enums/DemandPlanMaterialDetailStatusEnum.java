package com.inventory.middle.domain.plan.common.enums;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 21:02
 */
public enum DemandPlanMaterialDetailStatusEnum {

    ON(1,"开启"),
    OFF(0,"关闭"),
    DELETE(2,"剔除")
    ;


    DemandPlanMaterialDetailStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    private int code;

    private String desc;


}
