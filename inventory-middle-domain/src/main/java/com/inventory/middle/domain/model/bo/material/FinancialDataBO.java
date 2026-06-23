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
 * @version $Id: FinancialData, v 0.1 2019-11-14 18:56 Exp $
 */
@Data
public class FinancialDataBO implements Serializable {
    /**
     * 资产号
     */
    private String assertTag;

    /**
     * 次级编号
     */
    private String subAssertTag;

    /**
     * 利润中心名称
     */
    private String profitCenterName;

    /**
     * 利润中心
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905}, message = "利润中心不能为空")
    private String profitCenter;

    /**
     * 成本中心名称
     */
    private String costCenterName;

    /**
     * 成本中心
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905}, message = "成本中心不能为空")
    private String costCenter;

    /**
     * 产品线
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905}, message = "产品线不能为空")
    private String productLine;

    /**
     * 贸易伙伴
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905}, message = "贸易伙伴不能为空")
    private String tradePartner;

    /**
     * 供应商名称
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.VJ101, MaterialAdjustTypeEnum.RWW101, MaterialAdjustTypeEnum.DB405,
        MaterialAdjustTypeEnum.DB407, MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905}, message = "供应商名称不能为空")
    private String supplyName;

    /**
     * 供应商编码
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.VJ101, MaterialAdjustTypeEnum.RWW101, MaterialAdjustTypeEnum.DB405,
        MaterialAdjustTypeEnum.DB407, MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905, MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "供应商编码不能为空")
    private String supplyCode;

    /**
     * 客户名称
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.XS601, MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "客户名称不能为空")
    private String customer;

    /**
     * 客户编码
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.XS601, MaterialAdjustTypeEnum.DB405,
        MaterialAdjustTypeEnum.DB407}, message = "客户编码不能为空")
    private String customerCode;

    /**
     * 结算方式
     */
    private Integer settlementType;

    /**
     * 结算方式描述
     */
    private String settlementTypeDesc;

    /**
     * 营销活动编码
     */
    private String marketingNo;

    /**
     * 预算编码
     */
    private String budgetNo;

    /**
     * 内部订单号
     */
    private String internalOrderNo;

    /**
     * 备注1
     */
    private String remark;

}