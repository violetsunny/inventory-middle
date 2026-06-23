package com.inventory.middle.interfaces.web.plan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanOrderIssueDetailPageQueryReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long planOrderId;
    private Integer pageNum;
    private Integer pageSize;
}
