package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryDemand;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存-需求Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class InventoryDemandUpdateSpecification extends AbstractSpecification<InventoryDemand> {


    public InventoryDemandUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryDemand inventorydemand) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
