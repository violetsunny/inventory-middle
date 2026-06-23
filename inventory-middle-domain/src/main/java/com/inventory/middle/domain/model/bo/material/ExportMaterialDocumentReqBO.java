package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportMaterialDocumentReqBO implements Serializable {

    private String companyCode;

    private String materialDocGroup;

    private String logicalPlantNo;

    private String materialCode;

    private String materialDocType;

    private String materialDocNo;

    private String referBizNo;

    private String maxMaterialDocDate;

    private String maxAccountsPostingDate;

    private String minMaterialDocDate;

    private String minAccountsPostingDate;

    private Integer pageSize;

    private Integer pageNum;

    private String tenantId;
    /**
     * 调用接口应用服务标识
     */
    private String appKey;
}
