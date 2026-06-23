package com.inventory.middle.client.dto.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExportInventorySnapshotResDTO implements Serializable {

    private String materialCode;
    private String materialCategoryCode;
    private String warehouseName;
    private String logicalPlantNo;
    private String logicalPlantType;
    private String logicalPlantDesc;
    private String location;
    private String currency;
    private String uom;
    private BigDecimal total;
    private BigDecimal damaged;
    private BigDecimal unrestricted;
    private BigDecimal inspection;
    private String batchNo;
    private BigDecimal batchPrice;
    private Date createTime;
    private Date eta;
    private Date dueDate;
}
