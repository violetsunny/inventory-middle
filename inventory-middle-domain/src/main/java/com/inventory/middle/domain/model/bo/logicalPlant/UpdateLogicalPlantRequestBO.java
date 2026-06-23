package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

@Data
public class UpdateLogicalPlantRequestBO {

    private String tenantId;

    private Long logicalPlantId;

    private String logicalPlantName;

    private String province;

    private String city;

    private String region;

    private String address;

    private String operator;

}
