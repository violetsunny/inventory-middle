package com.inventory.middle.client.dto.map;

/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @description 移动平均价
 * @author kll
 * @date 2021-06-24
 */
@Data
public class InventoryMapDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * map流水号
     */
    private String mapCode;

    /**
     * map子流水号
     */
    private String mapSubCode;

    /**
     * 物料code
     */
    private String skuCode;

    /**
     * 逻辑仓
     */
    private String logicalPlantNo;

    /**
     * 移动平均价
     */
    private BigDecimal map;

    /**
     * 修改时间
     */
    private String updateTime;

}
