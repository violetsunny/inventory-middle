package com.inventory.middle.interfaces.web.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlanOrderPageQueryReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;
    private Date maxCreateTime;
    private Date minCreateTime;
    private String logicalPlantNo;
    private Integer status;
    private String materialCode;
    private Integer pageNum;
    private Integer pageSize;
}
