package com.inventory.middle.client.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 在途类转换型
 * @author dongguo.tao
 */

public enum TransferTransitTypeEnum {

    IN(0,"在途->在库"),
    OUT(1,"在库->在途");

    @Getter
    private int code;
    @Getter
    private String desc;


    TransferTransitTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static TransferTransitTypeEnum getByCode(Integer code){
        if (null == code){
            return null;
        }
        for (TransferTransitTypeEnum value : values()) {
            if (value.getCode() == code.intValue()){
                return value;
            }
        }
        return null;
    }

}
