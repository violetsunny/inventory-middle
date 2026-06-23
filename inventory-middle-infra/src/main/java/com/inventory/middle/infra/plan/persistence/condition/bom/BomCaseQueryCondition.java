package com.inventory.middle.infra.plan.persistence.condition.bom;

import lombok.Data;

import java.util.List;

@Data
public class BomCaseQueryCondition {

    private String code;

    private String name;

    private List<String> logicalPlantNos;

    private String companyName;

    private Integer type;

    private Integer status;

    private String tenantId;

    private List<Long> ids;
}
