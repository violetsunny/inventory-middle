package com.inventory.middle.infra.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class InventoryDemandPo {
    private Long id;

    private String materialCode;

    private String batchNo;

    private Date batchTime;

    private String owner;

    private String logicalPlantNo;

    private String storageLocationNo;

    private Byte demandType;

    private BigDecimal unrestricted;

    private BigDecimal damaged;

    private BigDecimal inspection;

    private String uom;

    private Date cancelDate;

    private Date deadlineDate;

    private String currency;

    private BigDecimal demandPrice;

    private Date createTime;

    private String creatorId;

    private Date updateTime;

    private String updatorId;

    private Boolean deleted;

    private String tenantId;
}