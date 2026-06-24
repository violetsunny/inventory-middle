package com.inventory.middle.application.plan.mq.message;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectOrderMessage implements Serializable {
    private String orderNo;
    private String documentNo;
    private String materialCode;
    private String materialDesc;
    private String logicalPlantNo;
    private String tenantId;
    private Date planDate;
    private Long amount;
    private Integer planFlag;
    private Date createTime;
    private String remark;
    private String stockUpPlanNo;
    private String unit;
    private String processNo;
    private String processName;
    private String userId;
    private String userName;
}
