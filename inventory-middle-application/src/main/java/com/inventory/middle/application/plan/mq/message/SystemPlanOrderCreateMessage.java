package com.inventory.middle.application.plan.mq.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SystemPlanOrderCreateMessage implements Serializable {
    private Long planId;
    private String materialCode;
    private String logicalPlantNo;
    private String transferLogicalPlantNo;
    private Integer planOrderQuantity;
    private Integer forecastInventory;
    private Date planIssueTime;
    private Date planReceivingTime;
    private JSONArray demandInfo;
    private JSONObject extAttrs;
    private String userId;
    private String userName;
    private String tenantId;
}
