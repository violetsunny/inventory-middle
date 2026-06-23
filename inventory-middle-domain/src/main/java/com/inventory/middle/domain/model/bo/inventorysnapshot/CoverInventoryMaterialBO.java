/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.inventorysnapshot;

import java.io.Serializable;

import lombok.Data;

/**
 * 覆盖物料数据
 *
 * @author kanglele
 * @version $Id: CoverInventoryMaterialDTO, v 0.1 2022/4/30 18:00 kanglele Exp $
 */
@Data
public class CoverInventoryMaterialBO implements Serializable {

    /**
     * 库存地点
     */
    private String storageLocationNo;
    /**
     * 物料code
     */
    private String materialCode;

}
