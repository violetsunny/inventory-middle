package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存预警规则明细Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class InventoryMonitorRuleLineUpdateSpecification extends AbstractSpecification<InventoryMonitorRuleLine> {


    public InventoryMonitorRuleLineUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryMonitorRuleLine inventorymonitorruleline) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
