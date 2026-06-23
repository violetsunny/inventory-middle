package com.inventory.middle.client.plan.config.dto;

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
public class PlanMaterialParamBatchCreateResDTO implements Serializable {

    private static final long serialVersionUID = -5231245377826375297L;
    /**
     * 导入总数
     */
    private Integer totalCount;

    /**
     * 失败总数
     */
    private Integer failedCount;

    /**
     * 失败详情列表
     */
    private List<PlanMaterialParamBatchCreateDetailDTO> failedDetails;
}
