package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询物料计划参数 入参
 * @date 2021/9/30 15:59
 */
@Data
public class PlanMaterialParamQueryReqDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = 2897206792410340668L;

    /**
     * 物料编码
     */
    @NotNull(message = "物料编码不能为空")
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    @NotNull(message = "逻辑仓编码不能为空")
    private String logicalPlantNo;
}
