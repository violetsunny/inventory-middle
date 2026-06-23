/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.enums.StockTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kanglele
 * @version $Id: WarehouseData, v 0.1 2019-11-08 18:35 Exp $
 */
@Data
public class WarehouseDataDTO implements Serializable {
    /**
     * 发货方名称
     */
    private String demand;

    /**
     * 发货销售订单号
     */
    private String demandSaleOrderNo;

    /**
     * 发货销售订单行号
     */
    private String demandSaleOrderItemNo;

    /**
     * 发货方逻辑仓库code
     */
    private String demandLogicalPlantNo;

    /**
     * 发货方逻辑仓库名称
     */
    private String demandLogicalPlantName;

    /**
     * 发货方库存地点code
     */
    private String demandStorageLocationNo;

    /**
     * 发货特殊库位编码
     */
    private String demandSpecialStorageLocationMark;

    /**
     * 发货方库存地点名称
     */
    private String demandStorageLocationName;

    /**
     * 发货方批次号
     */
    private String demandBatchNo;

    /**
     * 发货方库存类型 StockTypeEnum
     * @see StockTypeEnum
     *
     */
    private Integer demandStockType;

    /**
     * 发货方库存类型描述
     */
    private String demandStockTypeDesc;

    /**
     * 收货方名称
     */
    private String supply;

    /**
     * 收货销售订单号
     */
    private String supplySaleOrderNo;

    /**
     * 收货销售订单行号
     */
    private String supplySaleOrderItemNo;

    /**
     * 收货方逻辑仓库code
     */
    private String supplyLogicalPlantNo;

    /**
     * 收货方逻辑仓库名称
     */
    private String supplyLogicalPlantName;

    /**
     * 收货方库存地点code
     */
    private String supplyStorageLocationNo;

    /**
     * 收货特殊库位编码
     */
    private String supplySpecialStorageLocationMark;

    /**
     * 收货方库存地点名称
     */
    private String supplyStorageLocationName;

    /**
     * 收货方批次号
     */
    private String supplyBatchNo;

    /**
     * 收货方库存类型 StockTypeEnum
     * @see StockTypeEnum
     */
    private Integer supplyStockType;

    /**
     * 收货方库存类型描述
     */
    private String supplyStockTypeDesc;

    /**
     * 移动原因
     */
    private String adjustReason;

    /**
     * 移动类型
     */
    private String adjustType;

    /**
     * 出入库标识 +-
     */
    private String io;

}
