package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryAlert;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存报警日志Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
public class InventoryAlertUpdateSpecification extends AbstractSpecification<InventoryAlert> {


    public InventoryAlertUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryAlert inventoryalert) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
