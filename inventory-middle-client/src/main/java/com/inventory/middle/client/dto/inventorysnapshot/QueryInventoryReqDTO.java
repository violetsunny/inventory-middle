/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

/**
 * @author kll
 * @version $Id: QueryInventoryReqDTO, v 0.1 2021/6/29 10:01 Exp $
 */
@Data
public class QueryInventoryReqDTO extends BaseRequest {
    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 逻辑仓库code
     */
    private String logicalPlantNo;
    /**
     * 租户ID
     */
    private String tenantId;
}
