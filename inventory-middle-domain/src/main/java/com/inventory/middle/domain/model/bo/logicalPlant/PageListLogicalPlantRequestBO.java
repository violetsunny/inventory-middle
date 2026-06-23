package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

@Data
public class PageListLogicalPlantRequestBO {

    /**
     * 调用接口应用服务标识，必填
     */
    private String appKey;

    private String tenantId;

    private String logicalPlantNo;

    private String logicalPlantName;

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

    private int pageSize;

    private int pageNum;

}

