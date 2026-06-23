package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 备品备件斯可络发货单状态枚举
 * @author vincent.li
 * @date 2021-12-20 10:48:00
 */
@AllArgsConstructor
@Getter
public enum SpDeliveryOrderStateEnum {

    UN_DELIVER_PRINT(0,"未发货打印"),
    PART_DELIVER_PRINT(1,"已部分发货打印"),
    FINISHED(2,"已发货打印"),
    NO_NEED_DEAL(3,"无需处理"),
    NO_NEED_PRINT(4,"无需打印"),
    ;

    private Integer code;
    private String desc;

    public static SpDeliveryOrderStateEnum getByCode(Integer code){
        if (Objects.isNull(code)){
            return null;
        }
        for (SpDeliveryOrderStateEnum value : SpDeliveryOrderStateEnum.values()) {
            if (value.code.intValue() == code.intValue()){
                return value;
            }
        }
        return null;
    }
    public static String getDescByCode(Integer code){
        SpDeliveryOrderStateEnum statusEnum = getByCode(code);
        return Objects.isNull(statusEnum) ? null : statusEnum.getDesc();
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static boolean validBizState(Integer code){
        SpDeliveryOrderStateEnum stateEnum = getByCode(code);
        return validBizStateEnum(stateEnum);
    }

    public static boolean validBizStateEnum(SpDeliveryOrderStateEnum stateEnum){
        if (null == stateEnum){
            return false;
        }
        Integer code = stateEnum.getCode();
        return code.equals(SpDeliveryOrderStateEnum.PART_DELIVER_PRINT.getCode())
            || code.equals(SpDeliveryOrderStateEnum.FINISHED.getCode())
            || code.equals(SpDeliveryOrderStateEnum.UN_DELIVER_PRINT.getCode())
            || code.equals(SpDeliveryOrderStateEnum.NO_NEED_PRINT.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
