package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryDemand;
import org.springframework.stereotype.Component;

/**
 * 库存-需求Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class InventoryDemandFactory {

	public InventoryDemand createInventoryDemand() {
		return new InventoryDemand();
	}

}