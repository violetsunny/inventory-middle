/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.common.annotation.EnumValidator;
import com.inventory.middle.domain.common.annotation.MaterialAdjustTypeValid;
import com.inventory.middle.domain.model.enums.CurrencyEnum;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kanglele
 * @version $Id: QuantityData, v 0.1 2019-11-08 18:30 Exp $
 */
@Data
public class QuantityAndAmountDataBO implements Serializable {

    /**
     * 移动数量
     */
    @NotNull(message = "移动数量不能为空")
    @DecimalMin(value = "0", message = "移动数量不能小于0")
    private BigDecimal adjustQuantity;

    /**
     * 计量单位Id
     */
    @NotEmpty(message = "计量单位Id不能为空")
    private String uom;

    /**
     * 不含税单价
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.OQC101, MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.JC221, MaterialAdjustTypeEnum.XS621,
        MaterialAdjustTypeEnum.OZP707, MaterialAdjustTypeEnum.SC301, MaterialAdjustTypeEnum.VJ101,
        MaterialAdjustTypeEnum.RWW101}, message = "不含税单价不能为空且不能小于0")
    private BigDecimal price;

    /**
     * 不含税总价
     */
    private BigDecimal totalPrice;

    /**
     * 含税总价
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.XS601,
        MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "含税总价不能为空且不能小于0")
    private BigDecimal totalPriceTax;

    /**
     * 税码
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.XS601,
        MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "税码不能为空")
    private String taxCode;

    /**
     * 税码名称
     */
    private String taxName;

    /**
     * 税率
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.XS601,
        MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "税率不能为空且不能小于0")
    private BigDecimal taxRate;

    /**
     * 税额
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.XS601,
        MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "税额不能为空且不能小于0")
    private BigDecimal tax;

    /**
     * 货币
     */
    @NotEmpty(message = "货币不能为空")
    @EnumValidator(enumClass = CurrencyEnum.class, checkMethod = "checkByCode", message = "货币取值不正确")
    private String currency;

    /**
     * 汇率
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.XS601,
        MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB407}, message = "汇率不能为空且不能小于0")
    private BigDecimal exchangeRate;
}
