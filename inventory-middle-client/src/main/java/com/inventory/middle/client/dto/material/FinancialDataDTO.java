/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.enums.SettlementTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kanglele
 * @version $Id: FinancialData, v 0.1 2019-11-14 18:56 Exp $
 */
@Data
public class FinancialDataDTO implements Serializable {
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
    private String profitCenter;

    /**
     * 成本中心名称
     */
    private String costCenterName;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 产品线
     */
    private String productLine;

    /**
     * 贸易伙伴
     */
    private String tradePartner;

    /**
     * 供应商名称
     */
    private String supplyName;

    /**
     * 供应商编码
     */
    private String supplyCode;

    /**
     * 客户名称
     */
    private String customer;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 结算方式 SettlementTypeEnum
     * @see SettlementTypeEnum
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