package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

/**
 * @description 库存
 * @author peisheng.wang
 * @date 2021-06-16
 */
@Data
public class PagedInventorySnapshotReqBO {

    private Integer pageSize;

    private Integer pageNum;

    private Integer warning;

    private String materialCategoryCode;

    private Long warehouseId;

    private String materialCode;

    private Long logicalPlantId;

    private String logicalPlantNo;

    private Integer logicalPlantType;

    private String tenantId;

    /**
     * 调用接口应用服务标识
     */
    private String appKey;

}
