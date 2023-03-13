package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryPlan;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存-计划Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class InventoryPlanUpdateSpecification extends AbstractSpecification<InventoryPlan> {


    public InventoryPlanUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryPlan inventoryplan) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
