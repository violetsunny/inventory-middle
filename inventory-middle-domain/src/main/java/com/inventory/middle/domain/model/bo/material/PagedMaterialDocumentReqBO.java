package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @description 库存
 * @date 2021-06-16
 */
@Data
public class PagedMaterialDocumentReqBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyCode;

    private String materialDocGroup;

    private String logicalPlantNo;

    private String materialCode;

    private String outMaterialCode;

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
