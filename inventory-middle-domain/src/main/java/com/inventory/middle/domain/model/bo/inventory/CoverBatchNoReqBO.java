/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.inventory;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author kll
 * @version $Id: QueryMaterialCodeBatchNoReqVO, v 0.1 2021/6/18 17:12 Exp $
 */
@Data
public class CoverBatchNoReqBO implements Serializable {
    /**
     * 物料code
     */
    private List<String> materialCodeList;
    /**
     * 逻辑库存
     */
    private String logicalPlantNo;
    /**
     * 库存地点
     */
    private String storageLocationNo;
    /**
     * 租户id
     */
    private String tenantId;
}
