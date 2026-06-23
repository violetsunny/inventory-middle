/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.inventory.middle.domain.common.annotation.MaterialAdjustTypeValid;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;

import com.inventory.middle.domain.model.bo.BaseBO;
import lombok.Data;


/**
 * @author dongguo
 */
@Data
public class CreateMaterialLogicalPlantRefBO implements Serializable {

    private static final long serialVersionUID = 4450901134436311955L;

    /**
     * 租户id
     */
    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    /**
     * 物料编码
     */
    @NotBlank(message = "物料编码不能为空")
    private String materialCode;

    /**
     * 逻辑仓id
     */
    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    @NotBlank(message = "逻辑仓编码不能为空")
    private String logicalPlantNo;

    /**
     * 操作人id
     */
    @NotBlank(message = "操作人id不能为空")
    private String operatorId;

}
