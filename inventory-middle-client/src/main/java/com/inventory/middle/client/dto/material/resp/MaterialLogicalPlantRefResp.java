/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存物料和逻辑仓的关系
 *
 * @author vincent.li
 * @date 2022/5/7 11:18
 */
@Data
public class MaterialLogicalPlantRefResp implements Serializable {

    private static final long serialVersionUID = -7148398993366273721L;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 外部物料编码
     */
    private String outMaterialCode;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

}
