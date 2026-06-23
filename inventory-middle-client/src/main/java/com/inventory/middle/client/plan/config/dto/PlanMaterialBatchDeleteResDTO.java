package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量删除返回参数
 * @date 2021/11/9 11:14
 */
@Data
public class PlanMaterialBatchDeleteResDTO implements Serializable {

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
    private List<PlanMaterialBatchDeleteDetailDTO> failedDetails;
}
