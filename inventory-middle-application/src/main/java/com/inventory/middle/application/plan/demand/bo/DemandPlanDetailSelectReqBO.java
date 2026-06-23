package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/10/10 10:17
 */
@Data
public class DemandPlanDetailSelectReqBO {

    /**
     * 需求计划id
     */
    private Long demandPlanId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
