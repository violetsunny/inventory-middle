/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.query;

import lombok.Data;
import top.kdla.framework.dto.Query;

/**
 * @author kll
 * @version $Id: QueryMaterialCodeBatchNoReqVO, v 0.1 2021/6/18 17:12 Exp $
 */
@Data
public class MaterialBatchNoQuery extends Query {
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
    /**
     * 租户ID
     */
    private String tenantId;
}
