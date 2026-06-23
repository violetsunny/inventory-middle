/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.inventory;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kll
 * @version $Id: QueryMaterialCodeBatchNoReqVO, v 0.1 2021/6/18 17:12 Exp $
 */
@Data
public class QueryBatchNoReqBO implements Serializable {
    /**
     * 物料code
     */
    private String materialCode;
    /**
     * 逻辑库存Id
     */
    private Long logicalPlantId;
    /**
     * 库存地点Id
     */
    private Long storageLocationId;
}
