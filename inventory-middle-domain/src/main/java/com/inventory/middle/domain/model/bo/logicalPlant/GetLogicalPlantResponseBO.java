package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetLogicalPlantResponseBO {

    private Long logicalPlantId;

    private String tenantId;

    private String logicalPlantNo;

    private String logicalPlantName;

    private Integer type;

    private String typeDescription;

    private String companyCode;

    private Long warehouseId;

    private String warehouseNo;

    private String warehouseName;

    private String province;

    private String city;

    private String region;

    private String creatorId;

    private String address;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String lastUpdateTimeStr;

    private String updateUserId;
}
