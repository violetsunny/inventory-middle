package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

/**
 * BOM子件分页查询请求BO
 */
@Data
public class BomCaseChildrenQueryReqBO extends BomCaseDetailReqBO {

    private Integer pageNum;
    private Integer pageSize;
}
