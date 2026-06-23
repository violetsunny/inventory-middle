package com.inventory.middle.application.plan.config.bo;

import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询物料计划参数 入参
 * @date 2021/9/30 15:59
 */
@Data
public class PlanMaterialParamQueryReqBO extends PlanBaseBO implements Serializable {


    private static final long serialVersionUID = 2897206792410340668L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;
}