package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/30 10:34
 */
@Data
public class CancelDemandPlanMaterialReqBO extends BaseBo implements Serializable {
    private static final long serialVersionUID = -2631658051314221085L;

    /**
     * 需求计划物料表id
     */
    private Long demandPlanMaterialId;

    @Override
    public String toLog() {
        return toString();
    }
}
