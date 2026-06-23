package com.inventory.middle.domain.plan.common.enums;

/**
 * 消息来源枚举
 *
 * @author Vincent.Xiao
 * @date 2021/10/25
 */
public enum MessageSourceTypeEnum {

    CUSTOMER_ORDER_DEMAND(2, MessageTypeEnum.CUSTOMER_ORDER_DEMAND.getCode(), BizTypeEnum.DEMAND.getCode(), "客户订单需求"),
    PLAN_DEMAND(3, MessageTypeEnum.PLAN_DEMAND.getCode(), BizTypeEnum.DEMAND.getCode(), "预测需求"),
    BOM_DEMAND(4, MessageTypeEnum.BOM_DEMAND.getCode(), BizTypeEnum.DEMAND.getCode(), "BOM相关需求"),
    PLAN_ORDER(5, MessageTypeEnum.PLAN_ORDER.getCode(), BizTypeEnum.SUPPLY.getCode(), "计划订单"),
    PURCHASE_REQUEST(6, MessageTypeEnum.PURCHASE_REQUEST.getCode(), BizTypeEnum.SUPPLY.getCode(), "采购申请"),
    PURCHASE_ORDER(7, MessageTypeEnum.PURCHASE_ORDER.getCode(), BizTypeEnum.SUPPLY.getCode(), "采购订单"),
    DELIVERY_ORDER(8, MessageTypeEnum.DELIVERY_ORDER.getCode(), BizTypeEnum.SUPPLY.getCode(), "交运单"),
    PRODUCT_ORDER(9, MessageTypeEnum.PRODUCT_ORDER.getCode(), BizTypeEnum.SUPPLY.getCode(), "生产订单"),
    DELIVERY_ORDER_RECEIVE(10, MessageTypeEnum.DELIVERY_ORDER_RECEIVE.getCode(), BizTypeEnum.SUPPLY.getCode(), "交运单收货"),
    ;

    private final Integer code;
    private final Integer msgType;
    private final Integer bizType;
    private final String desc;

    MessageSourceTypeEnum(Integer code, Integer msgType, Integer bizType, String desc) {
        this.code = code;
        this.msgType = msgType;
        this.bizType = bizType;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public Integer getBizType() {
        return bizType;
    }

    public String getDesc() {
        return desc;
    }

    public static MessageSourceTypeEnum getEnumByMsgType(int msgType) {
        for (MessageSourceTypeEnum e : MessageSourceTypeEnum.values()) {
            if (e.getMsgType().equals(msgType)) {
                return e;
            }
        }
        return null;
    }

    public static MessageSourceTypeEnum getEnumByCode(int code) {
        for (MessageSourceTypeEnum e : MessageSourceTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
