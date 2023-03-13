/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料操作来源
 *
 * @author kanglele
 * @version $Id: MaterialDocSourceEnum, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum MaterialDocSourceEnum implements IEnum<String> {

    IMP("IMP", "走IMP页面人工操作库存"),
    INTERFACE("INTERFACE", "走接口系统操作库存"),
    ;

    private String code;
    private String desc;

    public static Boolean checkByCode(String code) {
        return Arrays.stream(MaterialDocSourceEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }

    public static MaterialDocSourceEnum enumByCode(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).findFirst().orElse(null);
    }

}
