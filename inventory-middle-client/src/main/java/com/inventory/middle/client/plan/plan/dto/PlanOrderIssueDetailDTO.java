package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 下发详情
 * @date 2021/10/25 16:20
 */
@Data
public class PlanOrderIssueDetailDTO implements Serializable {

    /**
     * 下发订单号
     */
    private String issueNo;

    /**
     * 计划订单ID
     */
    private Long planOrderId;

    /**
     * 下发数量
     */
    private Integer issueQuantity;

    /**
     * 完成数量
     */
    private Integer finishQuantity;

    /**
     * 当前状态
     */
    private String currentStatus;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    /**
     * 操作人姓名
     */
    private String operatorName;
}
