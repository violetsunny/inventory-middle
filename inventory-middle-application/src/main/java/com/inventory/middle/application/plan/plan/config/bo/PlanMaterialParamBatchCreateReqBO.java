package com.inventory.middle.application.plan.plan.config.bo;

import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/9/27 14:59
 */
@Data
public class PlanMaterialParamBatchCreateReqBO extends PlanBaseBO implements Serializable {

    private static final long serialVersionUID = 1592471391968732932L;
    /**
     * 批量新增集合
     */
    private List<PlanMaterialParameterBO> planMaterialParamList;
}