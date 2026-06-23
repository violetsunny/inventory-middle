package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kll
 */
@Data
public class ListStorageLocationByAdjustRequestBO {
    private String tenantId;
    private String logicalPlantNo;
    private String adjustType;
    private Integer type;
}
