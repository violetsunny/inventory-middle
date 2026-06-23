/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;


/**
 * @author dongguo
 */
@Data
public class CreateMaterialLogicalPlantRefReqDTO extends BaseRequest {


    private static final long serialVersionUID = 6113948015587338231L;
    /**
     * 租户id
     *
     * 必填
     */
    private String tenantId;

    /**
     * 物料编码
     *
     * 必填
     */
    private String materialCode;

    /**
     * 逻辑仓id
     *
     * 必填
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     *
     * 必填
     */
    private String logicalPlantNo;

    /**
     * 操作人id
     *
     * 必填
     */
    private String operatorId;

}
