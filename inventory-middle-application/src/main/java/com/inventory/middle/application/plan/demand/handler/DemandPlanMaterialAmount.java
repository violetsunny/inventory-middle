package com.inventory.middle.application.plan.demand.handler;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/10/11 10:41
 */
@Data
@Builder
public class DemandPlanMaterialAmount {

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 日期
     */
    private Date planDate;


    /**
     * 1：预测需求数量
     * 2：订单需求数量
     */
    private int type;

    /**
     * 变化数量，正值累加，负值扣减
     */
    private long changeAmount;



    /**
     * 租户id
     */
    private String tenantId;



}
