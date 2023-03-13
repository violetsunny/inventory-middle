/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author kll
 * @version $Id: SettlementTypeEnum, v 0.1 2021/7/2 16:44 Exp $
 */
@AllArgsConstructor
@Getter
public enum SettlementTypeEnum {
    BANK_TRANSFER(100,"银行汇款"),
    BANK_ACCEPTANCE_DRAFT(101,"银行承兑汇票"),
    COMMODITY_ACCEPTANCE_BILL(102,"商品承兑汇票"),
    CHEQUE(103,"支票"),
    ;

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e->code.equals(e.getCode())).map(SettlementTypeEnum::getDesc).findFirst().orElse("");
    }
}
