package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventorySupply;
import org.springframework.stereotype.Component;

/**
 * 库存-供给Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class InventorySupplyFactory {

	public InventorySupply createInventorySupply() {
		return new InventorySupply();
	}

}