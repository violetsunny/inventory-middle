/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料凭证类型
 * @author kanglele
 * @version $Id: MaterialDocTypeEnum, v 0.1 2019-11-26 21:28 oyo Exp $
 */
@AllArgsConstructor
@Getter
public enum MaterialDocTypeEnum implements IEnum<Integer> {

    INVENTORY_MANAGEMENT(1,"库存管理类"),
    PERMANENT_ASSETS(2,"固定资产类"),
    SERVICE_TYPE(3,"服务类"),
    PROJECT_TYPE(4,"项目类"),
    RECEIPT_TYPE(5,"发票类"),
    ;

    private Integer code;
    private String desc;


    public static String valueByCode(Integer code){
        if(null==code){
            return "";
        }
        return Arrays.asList(values()).stream().filter(e->code.equals(e.getCode())).map(MaterialDocTypeEnum::getDesc).findFirst().orElse("");
    }

    public static boolean checkByCode(Integer code){
        return StringUtils.isNotBlank(valueByCode(code));
    }
}
