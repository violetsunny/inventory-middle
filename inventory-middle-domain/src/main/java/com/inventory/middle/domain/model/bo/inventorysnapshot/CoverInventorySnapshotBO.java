/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;

import lombok.Data;

/**
 * 覆盖批次快照数据
 *
 * @author kanglele
 * @version $Id: CoverInventorySnapshotDTO, v 0.1 2022/4/30 14:16 kanglele Exp $
 */
@Data
public class CoverInventorySnapshotBO extends BaseRequest {

    /**
     * 租户
     */
    private String tenantId;
}
