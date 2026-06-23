/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.common.annotation.MaterialAdjustTypeValid;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kanglele
 * @version $Id: WarehouseData, v 0.1 2019-11-08 18:35 Exp $
 */
@Data
public class WarehouseDataBO implements Serializable {
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
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.ODB411, MaterialAdjustTypeEnum.ZY501, MaterialAdjustTypeEnum.ZY502, MaterialAdjustTypeEnum.ZY503,
        MaterialAdjustTypeEnum.ZY504, MaterialAdjustTypeEnum.ZY505, MaterialAdjustTypeEnum.ZY506}, message = "发货方库存地点code不能为空")
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
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.ZY501, MaterialAdjustTypeEnum.ZY502, MaterialAdjustTypeEnum.ZY503, MaterialAdjustTypeEnum.ZY504,
        MaterialAdjustTypeEnum.ZY505, MaterialAdjustTypeEnum.ZY506}, message = "发货方批次号不能为空")
    private String demandBatchNo;

    /**
     * 发货方库存类型
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.ZY501, MaterialAdjustTypeEnum.ZY502, MaterialAdjustTypeEnum.ZY503, MaterialAdjustTypeEnum.ZY504,
        MaterialAdjustTypeEnum.ZY505, MaterialAdjustTypeEnum.ZY506}, message = "发货方库存类型不能为空")
    private Integer demandStockType;

    /**
     * 发货方库存类型
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
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.ODB411, MaterialAdjustTypeEnum.ZY501, MaterialAdjustTypeEnum.ZY502, MaterialAdjustTypeEnum.ZY503,
        MaterialAdjustTypeEnum.ZY504, MaterialAdjustTypeEnum.ZY505, MaterialAdjustTypeEnum.ZY506}, message = "收货方库存地点code不能为空")
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
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.ZY501, MaterialAdjustTypeEnum.ZY502, MaterialAdjustTypeEnum.ZY503, MaterialAdjustTypeEnum.ZY504,
        MaterialAdjustTypeEnum.ZY505, MaterialAdjustTypeEnum.ZY506}, message = "收货方批次号不能为空")
    private String supplyBatchNo;

    /**
     * 收货方库存类型
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.ZY501, MaterialAdjustTypeEnum.ZY502, MaterialAdjustTypeEnum.ZY503, MaterialAdjustTypeEnum.ZY504,
        MaterialAdjustTypeEnum.ZY505, MaterialAdjustTypeEnum.ZY506}, message = "收货方库存类型不能为空")
    private Integer supplyStockType;
    /**
     * 收货方库存类型
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
