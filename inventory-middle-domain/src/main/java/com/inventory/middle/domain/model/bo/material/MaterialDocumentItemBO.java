/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.common.annotation.MaterialAdjustTypeValid;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import com.inventory.middle.domain.model.enums.MaterialItemCategoryEnum;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kanglele
 */
@Data
public class MaterialDocumentItemBO implements Serializable {

    /**
     * 凭证ID
     */
    private Long materialDocId;
    /**
     * 凭证行项目ID
     */
    private Long materialDocItemId;
    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 批次ID
     */
    private String batchNo;

    /**
     * 出入库类型 1-入库 2-出库
     */
    private Integer itemCategory;

    /**
     * 出入库类型描述
     */
    private String itemCategoryDesc;

    /**
     * 凭证
     */
    @NotNull(message = "物料信息标签必须有")
    @Valid
    private MaterialDataBO materialData;

    /**
     * 仓库信息
     */
    @NotNull(message = "出入库位置标签必须有")
    @Valid
    private WarehouseDataBO warehouseData;

    /**
     * 数量和金额
     */
    @NotNull(message = "数量标签必须有")
    @Valid
    private QuantityAndAmountDataBO quantityData;

    /**
     * 财务
     */
    @Valid
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.CG101, MaterialAdjustTypeEnum.VJ101, MaterialAdjustTypeEnum.RWW101, MaterialAdjustTypeEnum.DB405,
        MaterialAdjustTypeEnum.DB407, MaterialAdjustTypeEnum.FP901, MaterialAdjustTypeEnum.FP903,
        MaterialAdjustTypeEnum.FP905,MaterialAdjustTypeEnum.XS601}, message = "财务标签不能为空")
    private FinancialDataBO financeData;

    /**
     * MAP
     */
    private MapJournalDataBO mapJournalData;

    /**
     * 补充信息
     */
    @Valid
    private MaterialExtDataBO materialExtData;

    // TODO: fromPOs 和 conversionMaterialDocumentItemBO 已迁移至 infra 层 converter，Domain 层不应依赖 infra entity


}