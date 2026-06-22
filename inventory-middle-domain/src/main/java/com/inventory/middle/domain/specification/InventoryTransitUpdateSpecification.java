package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryTransit;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存-在途Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class InventoryTransitUpdateSpecification extends AbstractSpecification<InventoryTransit> {


    public InventoryTransitUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryTransit inventorytransit) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
