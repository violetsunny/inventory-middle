package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

/**
 * @author zhouxinzhong
 * @date 2021/11/1 16:05
 */
@Data
public class MaterialReqBO {

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;
}
