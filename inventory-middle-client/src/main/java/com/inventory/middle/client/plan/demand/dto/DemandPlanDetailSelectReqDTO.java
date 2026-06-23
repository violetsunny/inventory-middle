package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/10/9 11:18
 * @description 需求计划查询详情
 */
@Data
public class DemandPlanDetailSelectReqDTO implements Serializable {

    private static final long serialVersionUID = 6841133566971520205L;

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
