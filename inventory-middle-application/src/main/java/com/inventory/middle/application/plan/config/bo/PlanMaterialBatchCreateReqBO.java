package com.inventory.middle.application.plan.config.bo;

import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量创建计划物料 请求参数
 * @date 2021/10/2 10:59
 */
@Data
public class PlanMaterialBatchCreateReqBO extends PlanBaseBO implements Serializable {

    private static final long serialVersionUID = 1592471391968732932L;

    /**
     * 计划方案Id
     */
    private Long planId;

    /**
     * 租户Id
     */
    private String tenantId;

    /**
     * 批量新增集合
     */
    private List<PlanMaterialBO> planMaterialList;
}