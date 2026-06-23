/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package com.inventory.middle.client.dto.inventorysnapshot;

import javax.validation.constraints.NotBlank;

import com.inventory.middle.client.dto.BaseRequest;

import lombok.Data;

/**
 * 覆盖批次快照数据
 *
 * @author kanglele
 * @version $Id: CoverInventorySnapshotDTO, v 0.1 2022/4/30 14:16 kanglele Exp $
 */
@Data
public class CoverInventorySnapshotDTO extends BaseRequest {

    /**
     * 租户
     */
    @NotBlank(message = "tenantId 租户不能为空")
    private String tenantId;
}
