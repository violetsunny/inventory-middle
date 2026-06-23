package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

/**
 * 需求计划物料
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DemandPlanMaterialBO extends BaseBo {

    private static final long serialVersionUID = -4419923436529355902L;

    @Override
    public String toLog() {
        return null;
    }
}
