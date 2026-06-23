package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 14:03
 */
@Data
public class DemandSupplyStockBoardDetailReqDTO implements Serializable {

    private static final long serialVersionUID = 7479900237594757040L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;
}
