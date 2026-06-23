package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;

@Data
public class UpdateStorageLocationRequestBO {

    private String tenantId;

    private Long logicalPlantId;

    private Long storageLocationId;

    private String description;

    private String storageLocationPosition;

    private String operator;

}
