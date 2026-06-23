package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

/**
 * @description 库存
 * @author peisheng.wang
 * @date 2021-06-16
 */
@Data
public class DetailInventorySnapshotReqBO {

    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 逻辑仓ID
     */
    private Long logicalPlantId;


    /**
     * 存储地点Id
     */
    private Long locationId;

    /**
     * 详情管理粒度
     */
    private Integer type;


    private String tenantId;

}
