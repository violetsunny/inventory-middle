package com.inventory.middle.client.enums.monitor;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dongguo
 */

public enum MonitorRuleDimensionEnum {

    ASSIGN_MATERIAL("指定物料"),
    LOGICAL_PLANT_MATERIAL("逻辑仓下所有物料");

    private String desc;

    public String getDesc() {
        return desc;
    }

    MonitorRuleDimensionEnum(String desc){
        this.desc = desc;
    }

    public static MonitorRuleDimensionEnum getByName(String name){
        if (StringUtils.isBlank(name)){
            return null;
        }
        for (MonitorRuleDimensionEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return value;
            }
        }
        return null;
    }

    public static boolean validate(String name){
        if (StringUtils.isBlank(name)){
            return false;
        }
        for (MonitorRuleDimensionEnum value : values()) {
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
        for (MonitorRuleDimensionEnum value : values()) {
            if (StringUtils.equals(value.name(), name)){
                return value.getDesc();
            }
        }
        return null;
    }

}
