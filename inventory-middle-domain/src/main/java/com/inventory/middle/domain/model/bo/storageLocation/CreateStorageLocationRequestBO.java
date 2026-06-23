package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;

@Data
public class CreateStorageLocationRequestBO {

    private String tenantId;

    private Integer locationType;

    private Long logicalPlantId;

    private String description;

    private String storageLocationPosition;

    private String operator;

}
