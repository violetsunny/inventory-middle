package com.inventory.middle.application.plan.config.bo;

import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量删除物料清单
 * @date 2021/11/5 19:29
 */
@Data
public class PlanMaterialBatchDeleteReqBO extends PlanBaseBO implements Serializable {

    private static final long serialVersionUID = 1592471391968732932L;

    /**
     * 计划方案Id
     */
    private Long planId;

    /**
     * 批量新增集合
     */
    private List<PlanMaterialBO> planMaterialList;
}