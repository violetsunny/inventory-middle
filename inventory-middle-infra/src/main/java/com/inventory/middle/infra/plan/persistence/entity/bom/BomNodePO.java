package com.inventory.middle.infra.plan.persistence.entity.bom;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BOM节点表 PO
 * isDelete -> deleted (from BasePO)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BomNodePO extends BasePO {

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
