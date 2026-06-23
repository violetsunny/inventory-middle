package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 14:05
 */
@Data
public class DemandSupplyStockBoardDetailChartDTO implements Serializable {

    private static final long serialVersionUID = 106164834678472803L;

    /**
     * 需求量
     */
    private Long demandAmount;

    /**
     * 供给量
     */
    private Long supplyAmount;
}
