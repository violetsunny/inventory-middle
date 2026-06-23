package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;

/**
 * @author hjs
 */
@Data
public class ListStorageLocationRequestBO {

    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;
    /**
     * 存储地点类型
     */
    private Integer locationType;
    /**
     * 存储地点描述
     */
    private String preciseDescription;
}
