/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kll
 * @version $Id: QueryInventoryResDTO, v 0.1 2021/6/29 9:59 Exp $
 */
@Data
public class QueryInventoryResDTO implements Serializable {
    /**
     * 库存信息
     */
    List<InventorySnapshotDTO> inventorys;
}
