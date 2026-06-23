package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

@Data
public class CreateLogicalPlantRequestBO {

    private String tenantId;

    private String logicalPlantName;

    private Integer logicalPlantTypeCode;

    private String companyCode;

    private Long warehouseId;

    private String province;

    private String city;

    private String region;

    private String address;

    private String operator;

}
