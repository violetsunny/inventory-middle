/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料操作类型
 * @author kanglele
 * @version $Id: MaterialType, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum DeletedEnum implements IEnum<Integer> {

    FALSE(0,false,"未删除"),
    TRUE(1,true,"已删除"),
    ;

    private Integer code;
    private Boolean deleted;
    private String desc;
}
