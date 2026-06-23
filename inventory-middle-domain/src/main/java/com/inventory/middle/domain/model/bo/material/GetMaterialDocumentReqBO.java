/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kll
 * @version $Id: GetMaterialDocumentReqDTO, v 0.1 2021/6/17 20:12 Exp $
 */
@Data
public class GetMaterialDocumentReqBO implements Serializable {

    /**
     * 单据号
     */
    private String originalNo;

    /**
     * 单据类型 默认物料凭证单据
     */
    private Integer originalNoType;

    /**
     * 租户
     */
    private String tenantId;
}
