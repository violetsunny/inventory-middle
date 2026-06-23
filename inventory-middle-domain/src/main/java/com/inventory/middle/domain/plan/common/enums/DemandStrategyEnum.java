package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 需求响应策略枚举
 *
 * @author peisheng.wang
 */
@Getter
public enum DemandStrategyEnum {

    MTS10("MTS10", "面向库存生产【产品预测】"),
    MTS11("MTS11", "面向库存生产【订单和产品预测-根据较大值】"),
    MTS12("MTS12", "面向库存生产【订单和产品预测-根据冲销结果】"),
    MTS13("MTS13", "面向库存生产【订单和产品预测-根据时区】"),
    MTO30("MTO30", "面向订单生产【销售订单】"),
    MTP("MTP", "面向项目订单生产【项目订单】"),
    ;

    DemandStrategyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public static DemandStrategyEnum getByCode(String code) {
        for (DemandStrategyEnum e : DemandStrategyEnum.values()) {
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
