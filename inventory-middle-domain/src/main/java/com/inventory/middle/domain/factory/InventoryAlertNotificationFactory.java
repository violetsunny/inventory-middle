package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import org.springframework.stereotype.Component;

/**
 * 库存报警通知记录Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class InventoryAlertNotificationFactory {

	public InventoryAlertNotification createInventoryAlertNotification() {
		return new InventoryAlertNotification();
	}

}