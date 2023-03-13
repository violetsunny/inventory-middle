package com.inventory.middle.client.enums;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 日志行为 枚举
 * @author dongguo.tao
 *
 * 需要新的类型，请在此拓展枚举
 *
 * 此处枚举是部分类型只对外暴露查询的枚举，注意和common中的LogActionEnum枚举保持一致
 *
 */

public enum LogActionEnum {

    DEFAULT(0,"默认"),
    CREATE(10,"创建"),
    UPDATE(20,"修改"),
    DELETED(30,"删除"),

    MANUFACTURER_IN_STOCK(100, "厂商入库"),
    OCCUPY_ACCESSORIES_FLOW_CODE_SUCCESS(101, "占用备件流转码成功"),
    USE_ACCESSORIES_FLOW_CODE(102, "使用备件流转码"),
    REGENERATE_ACCESSORIES_FLOW_CODE_DISCARD(103, "重新生成码，废弃"),
    PC_APPLY_FLEEING(104,"PC发起调拨申请"),
    FLEEING_OUT_ACCEPT(105,"调出审批通过"),
    FLEEING_REJECT(106,"调拨申请拒绝"),
    DELIVER_ORDER(107,"厂商发货出库"),
    COMPENSATE_PRINT_CODE(108,"补打"),
    OCCUPY_ACCESSORIES_FLOW_CODE_FAILED(109, "占用备件流转码失败"),
    REGENERATE_ACCESSORIES_FLOW_CODE_NEW(110, "重新生成码，新增"),
    APP_APPLY_FLEEING(111,"APP发起调拨申请"),
    FLEEING_IN_ACCEPT(112,"调入审批通过"),
    DELIVER_COMPENSATE_PRINT_CODE(113,"发货补打"),
    ;

    @Getter
    private int code;
    @Getter
    private String desc;


    LogActionEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static LogActionEnum getByCode(Integer code){
        if (Objects.isNull(code)){
            return null;
        }
        for (LogActionEnum value : values()) {
            if (value.code == code){
                return value;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code){
        LogActionEnum moduleEnum = getByCode(code);
        return Objects.isNull(moduleEnum) ? null : moduleEnum.getDesc();
    }

    public static List<Integer> listByFilterCodes(List<Integer> filterCodes){
        if (CollectionUtils.isEmpty(filterCodes)){
            filterCodes = new ArrayList<>();
        }
        List<Integer> finalFilterCodes = filterCodes;
        return Arrays.stream(values())
                .filter(e -> !finalFilterCodes.contains(e.getCode()))
                .map(LogActionEnum::getCode).collect(Collectors.toList());
    }


}
