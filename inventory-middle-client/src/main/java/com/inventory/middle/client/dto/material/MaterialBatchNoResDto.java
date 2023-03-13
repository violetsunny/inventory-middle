/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kll
 * @version $Id: QueryMaterialCodeBatchNoResVO, v 0.1 2021/6/18 17:27 Exp $
 */
@Data
public class MaterialBatchNoResDto implements Serializable {
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
     * 物料批次
     */
    private List<MaterialBatchNoDto> materialBatchNos;
}
