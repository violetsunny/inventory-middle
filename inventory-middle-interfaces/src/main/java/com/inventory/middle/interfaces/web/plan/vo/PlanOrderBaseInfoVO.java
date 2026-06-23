package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlanOrderBaseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;
    private Integer createTypeCode;
    private String createTypeDesc;
    private Date createTime;
    private Date planIssueTime;
    private Date realIssueTime;
    private String operatorName;
    private Integer planTypeCode;
    private String planTypeDesc;
    private Integer statusCode;
    private String statusDesc;
    private Date confirmTime;
    private Date planReceivingTime;
    private Date realReceivingTime;
    private String plannerName;
    private String plannerId;
}
