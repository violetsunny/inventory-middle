package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存预警规则Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class InventoryMonitorRuleUpdateSpecification extends AbstractSpecification<InventoryMonitorRule> {


    public InventoryMonitorRuleUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryMonitorRule inventorymonitorrule) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
