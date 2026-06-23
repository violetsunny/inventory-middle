package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PageQueryPlanOrderIssueDetailResVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String issueNo;
    private Long planOrderId;
    private Integer issueQuantity;
    private Integer finishQuantity;
    private String currentStatus;
    private Date updateTime;
    private Date issueTime;
}
