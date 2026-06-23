package com.inventory.middle.domain.plan.common.enums;

/**
 * 消息类型枚举
 *
 * @author Vincent.Xiao
 * @date 2021/10/25
 */
public enum MessageTypeEnum {

    CUSTOMER_ORDER_DEMAND(101, "订单需求"),
    PLAN_DEMAND(102, "预测需求"),
    BOM_DEMAND(103, "BOM需求"),
    PROJECT_ORDER_DEMAND(104, "项目订单需求"),

    PLAN_ORDER(201, "计划订单"),
    PURCHASE_REQUEST(202, "采购申请"),
    PURCHASE_ORDER(203, "采购订单"),
    DELIVERY_ORDER(204, "交运单"),
    PRODUCT_ORDER(205, "生产订单"),
    DELIVERY_ORDER_RECEIVE(206, "交运单收获"),

    PURCHASE_FINISH(305, "采购完成"),
    ;

    private final Integer code;
    private final String desc;

    MessageTypeEnum(Integer code, String desc) {
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
