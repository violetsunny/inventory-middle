package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 库存报警通知记录Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class InventoryAlertNotificationUpdateSpecification extends AbstractSpecification<InventoryAlertNotification> {


    public InventoryAlertNotificationUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryAlertNotification inventoryalertnotification) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
