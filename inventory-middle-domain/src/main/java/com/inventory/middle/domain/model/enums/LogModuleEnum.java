package com.inventory.middle.domain.model.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 日志模块 枚举
 * @author dongguo.tao
 *
 * 需要新的类型，请在此拓展枚举
 *
 * 此处枚举是全部类型的枚举。client中是部分对外暴露查询的枚举， 维护枚举时按需维护两处，注意和client中的LogActionEnum枚举保持一致
 *
 */

public enum LogModuleEnum {

    DEFAULT(0,"默认"),
    WAREHOUSE(10,"物理仓"),
    LOGICAL_PLANT(20,"逻辑仓"),
    MONITOR(30,"预警"),
    INBOUND(40,"入库"),
    OUTBOUND(50,"出库"),
    IN_OUT_BOUND(60,"出入库"),
    CANCEL(70,"取消"),
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


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
