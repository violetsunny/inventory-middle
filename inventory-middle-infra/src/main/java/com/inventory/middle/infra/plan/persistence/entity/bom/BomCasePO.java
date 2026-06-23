package com.inventory.middle.infra.plan.persistence.entity.bom;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BOM单表 PO
 * 注意：BasePO 已包含 creatorId, updatorId, createTime, updateTime, deleted
 * createUserId -> creatorId, updateUserId -> updatorId, isDelete -> deleted
 * DROP: createUserName, updateUserName (BasePO 中没有)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BomCasePO extends BasePO {

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
}
