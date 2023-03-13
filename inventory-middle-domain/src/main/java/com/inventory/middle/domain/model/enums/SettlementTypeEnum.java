/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * @author kll
 * @version $Id: SettlementTypeEnum, v 0.1 2021/7/2 16:44 Exp $
 */
@AllArgsConstructor
@Getter
public enum SettlementTypeEnum implements IEnum<Integer> {
    BANK_TRANSFER(100, "银行汇款"),
    BANK_ACCEPTANCE_DRAFT(101, "银行承兑汇票"),
    COMMODITY_ACCEPTANCE_BILL(102, "商品承兑汇票"),
    CHEQUE(103, "支票"),
    ;

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(SettlementTypeEnum::getDesc).findFirst().orElse("");
    }

    public static Boolean checkByEmpty(Integer code) {
        //只有非空的时候才校验数值是否正确
        return Objects.isNull(code) || Arrays.stream(values()).anyMatch(v -> v.getCode().equals(code));
    }
}
