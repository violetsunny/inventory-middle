package com.inventory.middle.client.dto.logicalPlant;

import lombok.Data;

import java.io.Serializable;

/**
 * 逻辑仓返回对象
 */
@Data
public class LogicalPlantResponse implements Serializable {
    /**
     * 逻辑仓的id
     */
    private Long logicalPlantId;
    /**
     * 逻辑仓的编号
     */
    private String logicalPlantNo;
    /**
     * 逻辑仓的名称
     */
    private String logicalPlantName;
    /**
     * 逻辑仓所属公司的code
     */
    private String companyCode;
    /**
     * 仓库Id
     */
    private Long warehouseId;
}
