package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/1 15:33
 */
@Data
public class MaterialReqDTO implements Serializable {

    private static final long serialVersionUID = -3786784559592705042L;

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
