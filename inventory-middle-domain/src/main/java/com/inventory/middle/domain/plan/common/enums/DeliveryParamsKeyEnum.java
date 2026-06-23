package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划方案投放参数key枚举
 *
 * @author peisheng.wang
 */
@Getter
public enum DeliveryParamsKeyEnum {

    PURCHASER("purchaser", "采购人key"),
    ;

    DeliveryParamsKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public static DeliveryParamsKeyEnum getByCode(String code) {
        for (DeliveryParamsKeyEnum e : DeliveryParamsKeyEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
