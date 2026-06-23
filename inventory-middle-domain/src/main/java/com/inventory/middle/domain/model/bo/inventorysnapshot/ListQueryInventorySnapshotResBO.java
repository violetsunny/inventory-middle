package com.inventory.middle.domain.model.bo.inventorysnapshot;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author dongguo
 */
@Data
public class ListQueryInventorySnapshotResBO implements Serializable {

    private static final long serialVersionUID = -6818362879749598837L;

    private String materialCode;
    private String materialCategoryCode;
    private Long warehouseId;
    private Long logicalPlantId;
    private String logicalPlantNo;
    private Integer logicalPlantType;
    private BigDecimal unrestricted;
    private BigDecimal  damaged;
    private BigDecimal inspection;
    private BigDecimal total;
    private String uom;
    private String tenantId;
    private String currency;

}
