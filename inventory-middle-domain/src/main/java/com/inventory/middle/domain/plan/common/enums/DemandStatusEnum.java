package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;


@Getter
public enum DemandStatusEnum {

    ON(1,"开启"),
    OFF(0,"关闭"),
    INVALID(2,"删除")
    ;


    DemandStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;

    private String desc;

    public static DemandStatusEnum getByCode(Integer value) {
        for (DemandStatusEnum e : DemandStatusEnum.values()) {
            if (e.getCode().equals(value)) {
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
