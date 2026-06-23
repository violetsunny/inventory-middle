package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

import java.util.List;

@Data
public class ListLogicalPlantsRequestBO {

    private String tenantId;

    private String companyCode;

    private List<Integer> types;
}
