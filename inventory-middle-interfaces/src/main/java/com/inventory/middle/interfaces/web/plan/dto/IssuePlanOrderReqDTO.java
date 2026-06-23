package com.inventory.middle.interfaces.web.plan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IssuePlanOrderReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long planOrderId;
    private Integer issueQuantity;
}
