package com.inventory.middle.domain.plan.common.enums;

import java.util.Arrays;

public enum DemandTypeEnum {
    PREDICT(1, "预测"), PROJECT_ORDER(2, "项目订单");

    private int code;
    private String desc;

    DemandTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() { return code; }
    public String getDesc() { return desc; }

    public static DemandTypeEnum of(int code) {
        return Arrays.stream(DemandTypeEnum.values())
                .filter(item -> item.getCode() == code)
                .findFirst().orElse(null);
    }
}
