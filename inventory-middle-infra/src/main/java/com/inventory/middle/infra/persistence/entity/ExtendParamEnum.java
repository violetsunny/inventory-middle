/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.infra.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扩展参数类型枚举
 * @author dongguo.tao
 */
@AllArgsConstructor
@Getter
public enum ExtendParamEnum {

    CODE_APPLY_APPROVAL_ID("applyApprovalRecordId","码申请审核id"),
    ;

    private String key;

    private String desc;
}
