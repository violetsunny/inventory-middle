package com.inventory.middle.infra.persistence.entity;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author hjs
 * @date 2021/12/10
 */
@Data
public class PageQueryMaterialPlantRefRequestPO implements Serializable {


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
