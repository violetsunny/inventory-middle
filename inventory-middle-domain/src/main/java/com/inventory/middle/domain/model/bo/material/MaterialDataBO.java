/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.common.annotation.MaterialAdjustTypeValid;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kanglele
 * @version $Id: MaterialDocData, v 0.1 2019-11-20 21:20 Exp $
 */
@Data
public class MaterialDataBO implements Serializable {

    /**
     * 凭证ID
     */
    private Long materialDocId;

    /**
     * 凭证code
     */
    private String materialDocNo;

    /**
     * 凭证行项目ID
     */
    private Long materialDocItemId;

    /**
     * 物料code
     */
    @NotBlank(message = "物料编码不能为空")
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料品类
     */
    private String materialCategoryCode;

    /**
     * 物料重量
     */
    private BigDecimal materialWeight;

    /**
     * 物料重量单位
     */
    private String weightUnit;

    /**
     * 物料体积
     */
    private BigDecimal materialVolume;

    /**
     * 物料体积单位
     */
    private String volumeUnit;

    /**
     * 评估类
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903, MaterialAdjustTypeEnum.FP905}, message = "评估类不能为空")
    private String valuation;

    /**
     * 备注1
     */
    private String remark1;

    /**
     * 备注2
     */
    private String remark2;

}
