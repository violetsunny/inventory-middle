package com.inventory.middle.client.dto.map;

/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @author kll
 * @version $Id: QueryInventoryMap, v 0.1 2021/7/5 15:57 Exp $
 */
@Data
public class QueryInventoryMapDTO implements Serializable {
    /**
     * 物料code
     */
    private String skuCode;
    /**
     * 逻辑仓
     */
    private String logicalPlantNo;
    /**
     * 租户
     */
    private String tenantId;
}
