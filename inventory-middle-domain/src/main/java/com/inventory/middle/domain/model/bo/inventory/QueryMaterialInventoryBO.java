/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.inventory;

import com.inventory.middle.domain.model.enums.InventorySupplyTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询库存
 * @author kll
 * @version $Id: QueryMaterialInventoryBO, v 0.1 2021/6/23 17:22 Exp $
 */
@Data
public class QueryMaterialInventoryBO implements Serializable {
    /**
     * 物料
     */
    private String materialCode;
    /**
     * 逻辑仓
     */
    private String logicalPlantNo;
    /**
     * 批次
     */
    private String batchNo;
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * supply类型
     * @see InventorySupplyTypeEnum
     */
    private Integer supplyType;
}
