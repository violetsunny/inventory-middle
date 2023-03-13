/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料操作类型
 *
 * @author kanglele
 * @version $Id: MaterialDocCategoryEnum, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum MaterialDocCategoryEnum implements IEnum<Integer> {

    IN(1, "入库"),
    OUT(2, "出库"),
    INOUT(3, "出入库"),
    CANCEL(4, "取消");

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (null == code) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(MaterialDocCategoryEnum::getDesc).findFirst().orElse("");
    }

    public static boolean checkByCode(Integer code) {
        return StringUtils.isNotBlank(valueByCode(code));
    }

    public static MaterialDocCategoryEnum tansfer(Integer code){
        if(Objects.isNull(code)){
            return null;
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).findFirst().orElse(null);
    }
}
