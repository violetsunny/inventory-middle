package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryAlert;
import org.springframework.stereotype.Component;

/**
 * 库存报警日志Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
@Component
public class InventoryAlertFactory {

	public InventoryAlert createInventoryAlert() {
		return new InventoryAlert();
	}

}