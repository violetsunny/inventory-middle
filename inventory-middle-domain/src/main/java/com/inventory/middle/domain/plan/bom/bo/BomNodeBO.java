package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * BOM节点BO
 */
@Data
public class BomNodeBO implements Serializable {

    private static final long serialVersionUID = 3401559015279513358L;

    private Long id;
    private Long bomCaseId;
    private String materialCode;
    private String materialDesc;
    private String materialUnit;
    private String materialAttr;
    private String materialSpec;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Long amount;
    private Integer type;
    private String tenantId;
    private Integer status;
}
