package com.inventory.middle.application.plan.mq.message;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class PurchaseFinishMessage implements Serializable {
    private String bizNo;
    private String sourceNo;
    private Date realReceivingTime;
    private Integer finishQuantity;
    private String currentStatus;
    private String userId;
    private String userName;
    private String tenantId;
}
