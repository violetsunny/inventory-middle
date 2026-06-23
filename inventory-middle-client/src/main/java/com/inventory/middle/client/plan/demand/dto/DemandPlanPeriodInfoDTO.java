package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:xinao
 * @date:2021/9/28 17:24
 */
@Data
public class DemandPlanPeriodInfoDTO implements Serializable {
    private static final long serialVersionUID = -6793813136446341594L;


    /**
     * 周期信息
     */
    private String planPeriod;


    /**
     * 预测需求数量
     */
    private String planAmount;


}
