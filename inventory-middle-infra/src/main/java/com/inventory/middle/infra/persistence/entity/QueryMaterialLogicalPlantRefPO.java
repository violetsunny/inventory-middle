package com.inventory.middle.infra.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-10-13 14:16:44
 */
@Data
public class QueryMaterialLogicalPlantRefPO implements Serializable {

    private static final long serialVersionUID = -2725767030231875479L;

    /**
     * 主键id
     */
    private Long id;

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

    /**
     * 是否删除 0-否；1-是
     */
    private Integer deleted;
}
