package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

/**
 * BOM单详情请求BO
 */
@Data
public class BomCaseDetailReqBO {

    private Long bomCaseId;
    private String tenantId;
}
