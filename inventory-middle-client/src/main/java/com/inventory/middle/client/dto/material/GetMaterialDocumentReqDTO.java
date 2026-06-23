/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author kll
 * @version $Id: GetMaterialDocumentReqDTO, v 0.1 2021/6/17 20:12 Exp $
 */
@Data
public class GetMaterialDocumentReqDTO extends BaseRequest {

    /**
     * 单据号
     */
    @NotNull
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
