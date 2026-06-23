package com.inventory.middle.client.plan.dto.inventory;

import lombok.Data;

/**
 * 逻辑仓
 *
 * @author Danny.Lee
 * @date 2021/9/30
 */
@Data
public class LogicalPlant {

    private Long logicalPlantId;

    /** 逻辑仓编码 */
    private String logicalPlantNo;

    /** 逻辑仓名称 */
    private String logicalPlantName;

    /** 物理仓编码 */
    private String warehouseNo;

    /** 物理仓名称 */
    private String warehouseName;

    /** 公司主体编码 */
    private String companyCode;
}
