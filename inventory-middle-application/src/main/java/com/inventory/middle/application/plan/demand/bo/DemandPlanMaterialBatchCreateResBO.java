package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/9/26 14:21
 */
@Data
public class DemandPlanMaterialBatchCreateResBO extends BaseBo {

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
    private List<DemandPlanMaterialBatchCreateDetailBO> failDetails;

    @Override
    public String toLog() {
        return toString();
    }
}