package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;

import java.util.Date;

/**
 * 计划订单回执 PO。
 */
@Data
public class PlanOrderReceiptPO {

    private Long id;
    private String bizNo;
    private Integer sourceType;
    private String sourceNo;
    private Date realReceivingTime;
    private Integer finishQuantity;
    private String currentStatus;
    private Date createTime;
    private Date updateTime;
    private String originalInfo;
}
