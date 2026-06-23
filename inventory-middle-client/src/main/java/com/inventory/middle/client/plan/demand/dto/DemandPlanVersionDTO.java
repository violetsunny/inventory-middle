package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/13 16:15
 */
@Data
public class DemandPlanVersionDTO implements Serializable {

    private static final long serialVersionUID = -8967167030849695238L;

    /**
     * 需求计划id
     */
    Long demandPlanId;

    /**
     * 需求计划版本号
     */
    String planVersion;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

}
