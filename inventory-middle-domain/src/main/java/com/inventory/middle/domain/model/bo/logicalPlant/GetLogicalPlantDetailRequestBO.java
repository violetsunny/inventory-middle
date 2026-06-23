package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

@Data
public class GetLogicalPlantDetailRequestBO {

    private String tenantId;

    private Long logicalPlantId;

    private String logicalPlantNo;

    /**
     * 调用接口应用服务标识
     */
    private String appKey;
}
