package com.inventory.middle.application.plan.mq.message;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class MaterialPlanMessage implements Serializable {
    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
    private Date planDate;
}
