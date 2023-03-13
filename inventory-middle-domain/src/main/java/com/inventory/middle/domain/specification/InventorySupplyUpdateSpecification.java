package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventorySupply;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存-供给Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class InventorySupplyUpdateSpecification extends AbstractSpecification<InventorySupply> {


    public InventorySupplyUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventorySupply inventorysupply) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
