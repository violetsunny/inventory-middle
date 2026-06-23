package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * MQ Tag 枚举
 *
 * @author peisheng.wang
 * @date 2021/10/12
 */
@Getter
public enum MqTagEnum {

    CONFIRM_PLAN_ORDER_TAG("confirmPlanOrderTag", "确认计划订单"),
    ISSUE_PURCHASE_PLAN_ORDER_TAG("issuePurchasePlanOrderTag", "下发采购计划订单"),
    ISSUE_TRANSFER_PLAN_ORDER_TAG("issueTransferPlanOrderTag", "下发调拨计划订单"),
    ISSUE_PRODUCE_PLAN_ORDER_TAG("issueProducePlanOrderTag", "下发生产计划订单"),
    SYSTEM_PLAN_ORDER_CREATE_TAG("systemPlanOrderCreateTag", "系统创建计划订单"),
    PLAN_DEMAND_TAG("planDemandTag", "需求计划"),
    BOM_DEMAND_TAG("bomDemandTag", "BOM需求"),
    ;

    private final String code;

    private final String desc;

    MqTagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MqTagEnum getByCode(String code) {
        for (MqTagEnum e : MqTagEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
