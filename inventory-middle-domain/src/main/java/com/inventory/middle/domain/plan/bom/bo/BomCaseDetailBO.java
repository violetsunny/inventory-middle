package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

/**
 * BOM单及母件详情BO
 */
@Data
public class BomCaseDetailBO {

    private BomCaseBO bomCase;
    private BomNodeBO parent;
}
