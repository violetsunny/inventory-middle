package com.inventory.middle.domain.service.map.bo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 移动平均价 BO */
@Data
public class InventoryMapBO implements Serializable {
    private Long id;
    private String mapCode;
    private String mapSubCode;
    private String skuCode;
    private String logicalPlantNo;
    private BigDecimal map;
    private Date createTime;
    private String creatorId;
    private Date updateTime;
    private String updatorId;
    private Integer deleted;
    private String tenantId;
}
