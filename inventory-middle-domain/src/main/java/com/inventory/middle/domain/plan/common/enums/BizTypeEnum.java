package com.inventory.middle.domain.plan.common.enums;

/**
 * 业务类型枚举
 *
 * @author Vincent.Xiao
 * @date 2021/10/25
 */
public enum BizTypeEnum {

    DEMAND(1, "需求"),
    SUPPLY(2, "采购申请"),
    STOCK(3, "库存"),
    ;

    private final Integer code;
    private final String desc;

    BizTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
