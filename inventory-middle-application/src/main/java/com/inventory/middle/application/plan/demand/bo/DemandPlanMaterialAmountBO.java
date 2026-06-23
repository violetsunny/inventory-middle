package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/30 14:56
 */

@Data
public class DemandPlanMaterialAmountBO implements Serializable {

    private static final long serialVersionUID = 7254443966097861137L;
    /**
     * 周期id
     */
    private Long demandPlanMaterialPeriodId;

    /**
     * 数量
     */
    private Long planAmount;
}
