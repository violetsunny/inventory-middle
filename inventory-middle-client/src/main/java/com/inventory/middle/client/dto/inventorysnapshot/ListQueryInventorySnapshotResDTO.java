package com.inventory.middle.client.dto.inventorysnapshot;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author dongguo
 */
@Data
public class ListQueryInventorySnapshotResDTO implements Serializable {

    private static final long serialVersionUID = -6818362879749598837L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料类目
     */
    private String materialCategoryCode;

    /**
     * 物理仓id
     */
    private Long warehouseId;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓类型
     */
    private Integer logicalPlantType;

    /**
     * 良品量
     */
    private BigDecimal unrestricted;

    /**
     * 次品量
     */
    private BigDecimal  damaged;

    /**
     * 质检品量
     */
    private BigDecimal inspection;

    /**
     * 总量
     */
    private BigDecimal total;

    /**
     * 单位
     */
    private String uom;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 货币
     */
    private String currency;

}
