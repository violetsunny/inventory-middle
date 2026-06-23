package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialPlanInstanceCondition extends PageCondition {

    private String planCode;

    private String planVersion;

    private Date calStartTimeStart;

    private Date calStartTimeEnd;

    private Integer planType;

    private Integer status;

    private String materialCode;

    private String logicalPlantNo;

    private String tenantId;

    private List<String> materialCodes;

    private List<String> logicalPlantNos;

    private Long instanceId;
}
