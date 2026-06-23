package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:xinao
 * @date:2021/9/28 17:24
 */
@Data
public class DemandPlanMaterialPeriodBO extends BaseBo {
    private static final long serialVersionUID = -6793813136446341594L;


    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 具体的分配信息
     */
    private List<DemandPlanPeriodInfoBO> planPeriodInfoList;


    @Override
    public String toLog() {
        return this.toString();
    }
}
