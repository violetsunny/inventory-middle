package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

/**
 * BOM树渲染请求BO
 */
@Data
public class BomTreeRenderRequestBO {

    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
    private boolean nodeAsRoot;
    private boolean showLeaf;
    private boolean showEnable;
}
