package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryMap;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 移动平均价Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class InventoryMapUpdateSpecification extends AbstractSpecification<InventoryMap> {


    public InventoryMapUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryMap inventorymap) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
