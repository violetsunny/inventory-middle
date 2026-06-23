package com.inventory.middle.application.plan.config.bo;

import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量创建计划物料返参
 * @date 2021/10/2 11:07
 */
@Data
public class PlanMaterialBatchCreateResBO implements Serializable {

    private static final long serialVersionUID = -5210461962477607792L;
    /**
     * 新增总数
     */
    private Integer totalCount;

    /**
     * 失败总数
     */
    private Integer failedCount;

    /**
     * 失败详情列表
     */
    private List<PlanMaterialBatchCreateDetailBO> failedDetails;
}