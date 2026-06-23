package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询物料计划参数 入参
 * @date 2021/9/30 18:29
 */
@Data
public class PlanMaterialParamPageReqDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = -7600748560238311697L;

    /**
     * 租户Id
     */
    private String tenantId;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;


}
