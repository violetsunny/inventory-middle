package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/10/9 14:05
 * @description 一段计划日期内的需求数量
 */
@Data
public class PeriodDemandResultDTO implements Serializable {

    private static final long serialVersionUID = 9179418071155622527L;

    /**
     * 需求计划周期表id
     */
    private Long periodId;

    /**
     * 计划日期描述,格式形如2021/9/28-2021/10/3
     */
    private String planPeriod;

    /**
     * 预测需求数量
     */
    private Long planAmount;

    /**
     * 拓展信息
     */
    private String extInfo;
}
