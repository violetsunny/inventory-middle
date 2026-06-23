package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;

import java.util.List;

/**
 * @author kll
 */
@Data
public class ListStorageLocationByTypeBO {
    private String tenantId;
    private Long logicalPlantId;
    private List<Integer> types;
}
