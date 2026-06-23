package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 备品备件斯可络发货单明细状态枚举
 * @author vincent.li
 * @date 2021-12-20 10:48:00
 */
@AllArgsConstructor
@Getter
public enum SpDeliveryOrderDetailStateEnum {

    UN_DELIVER_PRINT(0,"未发货打印"),
    FINISHED(1,"已发货打印"),
    NO_NEED_DEAL(2,"无需处理"),
    NO_NEED_PRINT(3,"无需打印"),
    ;

    private Integer code;
    private String desc;

    public static SpDeliveryOrderDetailStateEnum getByCode(Integer code){
        if (Objects.isNull(code)){
            return null;
        }
        for (SpDeliveryOrderDetailStateEnum value : SpDeliveryOrderDetailStateEnum.values()) {
            if (value.code.intValue() == code.intValue()){
                return value;
            }
        }
        return null;
    }
    public static String getDescByCode(Integer code){
        SpDeliveryOrderDetailStateEnum statusEnum = getByCode(code);
        return Objects.isNull(statusEnum) ? null : statusEnum.getDesc();
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static boolean checkApprovalStatus(Integer code){
        SpDeliveryOrderDetailStateEnum statusEnum = getByCode(code);
        if (null == statusEnum){
            return false;
        }
        return code.equals(SpDeliveryOrderDetailStateEnum.NO_NEED_DEAL.getCode())
            || code.equals(SpDeliveryOrderDetailStateEnum.FINISHED.getCode())
            || code.equals(SpDeliveryOrderDetailStateEnum.NO_NEED_PRINT.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
