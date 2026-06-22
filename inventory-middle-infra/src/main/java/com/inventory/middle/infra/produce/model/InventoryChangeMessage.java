/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.infra.produce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存变化
 *
 * @author kll
 * @version $Id: InventoryChangeProduce, v 0.1 2021/6/24 12:52 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryChangeMessage implements Serializable {
    private static final long serialVersionUID = -8940959490627420084L;
    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 逻辑仓库code
     */
    private String logicalPlantNo;

    /**
     * 1-入库
     * 2-出库
     */
    private Integer inventoryCategory;

    /**
     * 移动数量
     */
    private BigDecimal adjustQuantity;

    /**
     * 不含税单价
     */
    private BigDecimal price;

    /**
     * 货币单位
     */
    private String currency;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 租户
     */
    private String tenantId;

    /**
     * 平均价code
     */
    private String mapCode;

    /**
     * map子流水code
     */
    private String mapSubCode;

    /**
     * 是否计算
     */
    private Boolean calMap;

    /**
     * 发送版本
     */
    private String version;

}
