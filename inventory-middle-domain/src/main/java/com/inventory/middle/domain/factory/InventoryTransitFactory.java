package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryTransit;
import org.springframework.stereotype.Component;

/**
 * 库存-在途Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class InventoryTransitFactory {

	public InventoryTransit createInventoryTransit() {
		return new InventoryTransit();
	}

}