/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.enums.CurrencyEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kanglele
 * @version $Id: QuantityData, v 0.1 2019-11-08 18:30 Exp $
 */
@Data
public class QuantityAndAmountDataDTO implements Serializable {

    /**
     * 移动数量
     */
    @NotNull(message = "adjustQuantity 移动数量不能为空")
    private BigDecimal adjustQuantity;

    /**
     * 计量单位id
     */
    @NotNull(message = "uom 计量单位Id不能为空")
    private Long uom;

    /**
     * 不含税单价
     */
    private BigDecimal price;

    /**
     * 不含税总价
     */
    private BigDecimal totalPrice;

    /**
     * 价税总价
     */
    private BigDecimal totalPriceTax;

    /**
     * 税码
     */
    private String taxCode;

    /**
     * 税码名称
     */
    private String taxName;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 货币 CurrencyEnum
     * @see CurrencyEnum
     */
    @NotEmpty(message = "currency 货币不能为空")
    private String currency;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;
}
