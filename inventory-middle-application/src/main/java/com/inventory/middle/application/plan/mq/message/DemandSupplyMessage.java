package com.inventory.middle.application.plan.mq.message;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class DemandSupplyMessage implements Serializable {
    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
    private Date planDate;
    private Integer sourceType;
    private Integer bizType;
    private Long amount;
    private String voucherNo;
    private String documentNo;
    private String businessIdRef;
    private int operateType = 2;
    private String userId;
    private String userName;
}
