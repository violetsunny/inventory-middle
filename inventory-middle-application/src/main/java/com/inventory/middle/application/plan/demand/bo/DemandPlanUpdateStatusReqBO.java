package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import lombok.Data;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 20:43
 */
@Data
public class DemandPlanUpdateStatusReqBO {


    /**
     * 需求计划id
     */
    private Long demandPlanId;

    /**
     * 状态
     */
    private int status;

    /**
     * 租户Id
     */
    private String tenantId;

    /**
     * 需求计划
     */
    private DemandPlanPO demandPlanPO;

}
