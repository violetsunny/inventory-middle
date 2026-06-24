package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;
import java.util.Date;

@Data
public class PlanDemandSupplyStockPO {
    private Long id;
    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
    private Date planDate;
    private Integer planElementType;
    private Integer bizType;
    private Long amount;
    private String voucherNo;
    private String documentNo;
    private String businessIdRef;
    private String exceptionCode;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
}
