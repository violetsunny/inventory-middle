/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料凭证操作渠道 枚举
 *
 * @author dongguo.tao
 */
@AllArgsConstructor
@Getter
public enum MaterialDocChannelActionEnum implements IEnum<String> {

    SN_IN_BUILDER("snMaterialDocInBuilderHandleChain", "数能期初入库"),
    SN_CANCEL_BUILDER("snMaterialDocCancelBuilderHandleChain", "数能期初取消"),
    ;

    private String code;
    private String desc;

}
