package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hjs
 * @date 2021/12/10
 */
@Data
public class PageQueryMaterialPlantRefRequestBO {

    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 物料编码，模糊查询
     */
    private String fuzzyMaterialCode;

    private Integer pageSize;

    private Integer pageNum;

}
