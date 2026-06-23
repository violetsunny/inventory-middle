package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 订单类型枚举
 *
 * @author zhouxinzhong
 * @date 2022/05/09 09:57
 */
@Getter
public enum OrderPlanTypeEnum {

    /**
     * 工程订单
     */
    PROJECT_ORDER(1, "工程订单"),


    ;

    private final Integer code;
    private final String desc;

    OrderPlanTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    OrderPlanTypeEnum of(Integer code) {
        return Arrays.stream(OrderPlanTypeEnum.values()).
                filter(t -> t.getCode().equals(code)).
                findFirst().
                orElse(null);
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
