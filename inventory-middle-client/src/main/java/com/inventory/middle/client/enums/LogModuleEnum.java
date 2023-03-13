package com.inventory.middle.client.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 日志模块 枚举
 * @author dongguo.tao
 *
 * 需要新的类型，请在此拓展枚举
 *
 * 此处枚举是部分类型只对外暴露查询的枚举，注意和common中的LogModuleEnum枚举保持一致
 *
 */

public enum LogModuleEnum {

    INBOUND(40,"入库"),
    OUTBOUND(50,"出库"),
    IN_OUT_BOUND(60,"出入库"),
    ANTI_FLEEING_GOODS_CODE(80,"窜货码"),
    DELIVERY_PRINT(90,"发货打印"),
    ;

    @Getter
    private int code;
    @Getter
    private String desc;


    LogModuleEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static LogModuleEnum getByCode(Integer code){
        if (Objects.isNull(code)){
            return null;
        }
        for (LogModuleEnum value : values()) {
            if (value.code == code){
                return value;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code){
        LogModuleEnum moduleEnum = getByCode(code);
        return Objects.isNull(moduleEnum) ? null : moduleEnum.getDesc();
    }

}
