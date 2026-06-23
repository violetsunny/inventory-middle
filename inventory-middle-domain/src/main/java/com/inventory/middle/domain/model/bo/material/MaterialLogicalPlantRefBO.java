/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.inventory.middle.domain.model.bo.BaseBO;
import lombok.Data;


/**
 * @author dongguo
 */
@Data
public class MaterialLogicalPlantRefBO extends BaseBO implements Serializable {


    private static final long serialVersionUID = 1968844141582534920L;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;


}
