package com.inventory.middle.client.enums.monitor;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dongguo
 */

public enum MonitorRuleTypeEnum {

    QUANTITY("数量预警"),
    ANNUAL_INSPECTION("年检预警"),
    /** 下面三种预警规则尚未规划 */
//    PERIOD("期效预警"),
//    STORAGE_AGE("库龄预警"),
//    AMOUNT("金额预警"),
    ;

    private String desc;

    public String getDesc() {
        return desc;
    }

    MonitorRuleTypeEnum(String desc){
        this.desc = desc;
    }

    public static boolean validate(String name){
        if (StringUtils.isBlank(name)){
            return false;
        }
        for (MonitorRuleTypeEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return true;
            }
        }
        return false;
    }

    public static MonitorRuleTypeEnum getByName(String name){
        if (StringUtils.isBlank(name)){
            return null;
        }
        for (MonitorRuleTypeEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return value;
            }
        }
        return null;
    }

    public static String getDescByName(String name){
        MonitorRuleTypeEnum typeEnum = getByName(name);
        if (null == typeEnum){
            return null;
        }
        return typeEnum.getDesc();
    }


}
