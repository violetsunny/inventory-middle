/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.inventory;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kll
 * @version $Id: UpdateInventoryMaterialDocBO, v 0.1 2021/6/23 16:31 Exp $
 */
@Data
public class UpdateInventoryMaterialDocBO implements Serializable {
    private String materialCode;
    private String batchNo;
    private Long materialDocId;
}
