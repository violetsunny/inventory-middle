/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kll
 * @version $Id: QueryMaterialCodeBatchNoReqVO, v 0.1 2021/6/18 17:12 Exp $
 */
@Data
public class QueryMaterialBatchNoReqBO implements Serializable {
    /**
     * 移动类型
     */
    private String adjustType;
    /**
     * 物料code
     */
    private String materialCode;
    /**
     * 逻辑库存
     */
    private String logicalPlantNo;
    /**
     * 库存地点
     */
    private String storageLocationNo;
    /**
     * 查询类型 1-收货方 2-发货方
     */
    private Integer queryType;
}
