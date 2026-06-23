package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

@Data
public class ListLogicalPlantRequestBO {

    private String tenantId;

    private String companyCode;

    private Integer type;


    private String logicalPlantNo;

    private String logicalPlantName;
    /**
     * 逻辑仓的名称（精确查询）
     */
    private String preciseLogicalPlantName;

    /**
     * 省代码
     */
    private String province;

    /**
     * 市代码
     */
    private String city;

    /**
     * 区代码
     */
    private String region;
}
