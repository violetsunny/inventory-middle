package com.inventory.middle.client.dto.map;

/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */

import com.inventory.middle.client.dto.PageRequest;

import lombok.Data;

/**
 * 分页查询移动平均价
 * @author kll
 * @version $Id: InventoryMapPageDTO, v 0.1 2021/6/29 15:03 Exp $
 */
@Data
public class InventoryMapPageDTO extends PageRequest {
    /**
     * sku
     */
    private String skuCode;
    /**
     * 租户
     */
    private String tenantId;

}
