package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PagedInventorySnapshotResBO implements Serializable {
    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 物料类目
     */
    private String materialCategoryCode;

    /**
     * 物理仓名称
     */
    private String warehouseName;

    /**
     * 逻辑仓Id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓代码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓类型
     */
    private String logicalPlantType;

    /**
     * 逻辑仓描述
     */
    private String logicalPlantDesc;

    /**
     * 逻辑仓sku移动平均价
     */
    private BigDecimal warehousePrice;

    /**
     * 批次不含税单价
     */
    private BigDecimal batchPrice;

    /**
     * 币种
     */
    private String currency;

    /**
     * 单位
     */
    private String uom;

    /**
     * 正品库存
     */
    private BigDecimal unrestricted;

    /**
     * 次品库存
     */
    private BigDecimal damaged;

    /**
     * 质检库存
     */
    private BigDecimal inspection;

    /**
     * 总库存
     */
    private BigDecimal total;

    /**
     * 在途库存总量
     */
    private BigDecimal inTransitTotal;

    /**
     * 告警标识
     */
    private String warningMark;

    /**
     * 偏移量
     */
    private Long offset;
}
