package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/9/26 14:21
 */
@Data
@Builder
public class PlanMaterialParamBatchCreateResBO implements Serializable {

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
    private List<PlanMaterialParamBatchCreateDetailBO> failedDetails;
}