package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 逻辑仓库 BO（domain 层使用，对应 infra.LogicalPlantDo） */
@Data
public class LogicalPlantBO implements Serializable {
    private Long id;
    private String tenantId;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer type;
    private String companyCode;
    private String warehouseNo;
    private Long warehouseId;
    private String province;
    private String city;
    private String region;
    private String address;
    private String creatorId;
    private String updatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
