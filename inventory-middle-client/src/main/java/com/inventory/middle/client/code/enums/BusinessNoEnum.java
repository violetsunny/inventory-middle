package com.inventory.middle.client.code.enums;

import lombok.Getter;

/**
 * 业务代码 枚举
 *
 * @author hjs
 * @date 2021/12/22
 */
@Getter
public enum BusinessNoEnum {

    SIKELUO("SIKELUO", "斯可络"),
    ;


    BusinessNoEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    private String description;

    public static BusinessNoEnum getByCode(String code) {
        for (BusinessNoEnum e : BusinessNoEnum.values()) {
            if (e.getCode().equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
    public static boolean isValidCode(String code) {
        return getByCode(code) != null;
    }

}
