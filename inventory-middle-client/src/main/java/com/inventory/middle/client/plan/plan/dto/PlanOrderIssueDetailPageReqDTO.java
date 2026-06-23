package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询计划订单下发详情参数
 * @date 2021/10/25 16:24
 */
@Data
public class PlanOrderIssueDetailPageReqDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = -1233787186393437326L;
    /**
     * 计划订单id
     */
    private Long planOrderId;

    /**
     * 租户id
     */
    private String tenantId;

}
