package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

/**
 * BOM清单BO
 */
@Data
public class BomCaseBO {

    private Long id;
    private String code;
    private String name;
    private String companyCode;
    private String companyName;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer type;
    private Integer status;
    private String remark;
    private String tenantId;
    private String userId;
    private String userName;
}
