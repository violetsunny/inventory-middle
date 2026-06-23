package com.inventory.middle.infra.plan.persistence.condition.demand;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDemandQueryCondition {

    private Long id;

    private String orderNo;

    private Integer orderType;

    private String materialCode;

    private String logicalPlantNo;

    private String tenantId;

    private Date planDate;

    private Long amount;

    private String extInfo;

    private Integer isDelete;
}
