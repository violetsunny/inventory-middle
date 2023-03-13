/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kll
 * @version $Id: MaterialCodeBatchNoVO, v 0.1 2021/6/18 17:30 Exp $
 */
@Data
public class MaterialBatchNoDto implements Serializable {
    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 批次
     */
    private String batchNo;

    /**
     * 批次价
     */
    private BigDecimal batchPrice;
}
