package com.inventory.middle.client.enums;

import lombok.Getter;

/**
 * @author dongguo
 */

public enum BaseStatusEnum {

    YES(1,"是"),
    NO(0,"否");

    @Getter
    private int code;
    @Getter
    private String desc;


    BaseStatusEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static boolean checkByCode(Integer code){
        if (null == code){
            return false;
        }
        for (BaseStatusEnum value : values()) {
            if (value.getCode() == code.intValue()){
                return true;
            }
        }
        return false;
    }

}
