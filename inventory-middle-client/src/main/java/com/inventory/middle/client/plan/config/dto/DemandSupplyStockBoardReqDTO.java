package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 11:06
 */
@Data
public class DemandSupplyStockBoardReqDTO implements Serializable {

    private static final long serialVersionUID = 5223690382421452919L;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * token
     */
    private String token;
}
