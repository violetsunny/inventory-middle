package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventorySnapshot;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存快照-实时Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class InventorySnapshotUpdateSpecification extends AbstractSpecification<InventorySnapshot> {


    public InventorySnapshotUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventorySnapshot inventorysnapshot) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
