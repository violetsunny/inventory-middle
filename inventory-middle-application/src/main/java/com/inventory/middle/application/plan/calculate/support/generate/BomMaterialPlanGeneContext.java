package com.inventory.middle.application.plan.calculate.support.generate;

import com.inventory.middle.application.plan.calculate.bo.MaterialPlanInstanceBO;
import lombok.Data;

/**
 * Created on 2021/12/9.
 *
 * @author Danny.Lee
 */
@Data
public class BomMaterialPlanGeneContext extends MaterialPlanGeneContext {

    private Long amount;

    private MaterialPlanInstanceBO root;

    private MaterialPlanInstanceBO parent;

    public BomMaterialPlanGeneContext(MaterialPlanGeneContext delegate) {
        super.setPlan(delegate.getPlan());
        super.setMaterial(delegate.getMaterial());
        super.setGeneType(delegate.getGeneType());
        super.setInitialStock(delegate.getInitialStock());
        super.setPlanMaterialParameter(delegate.getPlanMaterialParameter());
    }

}
