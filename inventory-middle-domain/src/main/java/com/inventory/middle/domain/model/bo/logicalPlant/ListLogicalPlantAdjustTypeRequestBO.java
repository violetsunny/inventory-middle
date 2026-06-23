package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

@Data
public class ListLogicalPlantAdjustTypeRequestBO {

    private String tenantId;

    private String companyCode;

    private String adjustType;
}
