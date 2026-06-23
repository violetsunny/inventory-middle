package com.inventory.middle.client.plan.demand.dto;

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
public class DemandPlanMaterialBatchCreateResDTO implements Serializable {

    private static final long serialVersionUID = -9158808376340404786L;
    /**
     * 导入总数
     */
    private Integer totalCount;


    /**
     * 失败总数
     */
    private Integer successCount;

    /**
     * 失败总数
     */
    private Integer failCount;

    /**
     * 失败详情列表
     */
    private List<DemandPlanMaterialBatchCreateDetailDTO> failDetails;
}
