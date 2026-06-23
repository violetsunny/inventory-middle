package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 下发订单 入参
 * @date 2021/10/20 18:19
 */
@Data
public class PlanOrderIssueReqDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 3949123749732409607L;

    /**
     * 计划订单id
     */
    private Long planOrderId;

    /**
     * 下发数量
     */
    private Integer issueQuantity;

}
