package com.inventory.middle.domain.plan.common.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 物料信息
 *
 * @author Danny.Lee
 */
@Data
public class MaterialBO implements Serializable {
    private static final long serialVersionUID = 3487252012667777049L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

    public static MaterialBO of(String materialCode, String logicalPlantNo, String tenantId) {
        MaterialBO material = new MaterialBO();
        material.materialCode = materialCode;
        material.logicalPlantNo = logicalPlantNo;
        material.tenantId = tenantId;
        return material;
    }
}
