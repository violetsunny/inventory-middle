package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportInventorySnapshotReqBO implements Serializable {

    private Long warehouseId;

    private Long logicalPlantId;

    private Integer logicalPlantType;

    private String materialCode;

    private String materialCategoryCode;

    private String tenantId;

    /**
     * 调用接口应用服务标识
     */
    private String appKey;
}
