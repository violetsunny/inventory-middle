package com.inventory.middle.client.enums.monitor;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dongguo
 */

public enum MonitorRuleSendModeEnum {

    SYSTEM("系统通知"),
    EMAIL("邮件"),
//    SMS("手机短信"),
    ;

    private String desc;

    public String getDesc() {
        return desc;
    }

    MonitorRuleSendModeEnum(String desc){
        this.desc = desc;
    }

    public static boolean validate(String name){
        if (StringUtils.isBlank(name)){
            return false;
        }
        for (MonitorRuleSendModeEnum value : values()) {
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
        for (MonitorRuleSendModeEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return value.getDesc();
            }
        }
        return null;
    }

}
