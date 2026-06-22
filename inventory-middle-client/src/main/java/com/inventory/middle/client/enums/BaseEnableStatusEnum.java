package com.inventory.middle.client.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dongguo
 */

public enum BaseEnableStatusEnum {

    ENABLE("启用"),
    DISABLE("停用");

    private String desc;

    public String getDesc() {
        return desc;
    }

    BaseEnableStatusEnum(String desc){
        this.desc = desc;
    }

    public static boolean validate(String name){
        if (StringUtils.isBlank(name)){
            return false;
        }
        for (BaseEnableStatusEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return true;
            }
        }
        return false;
    }

    public static String getDescByName(String name){
        if (StringUtils.isBlank(name)){
            return null;
        }
        for (BaseEnableStatusEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return value.getDesc();
            }
        }
        return null;
    }

}
