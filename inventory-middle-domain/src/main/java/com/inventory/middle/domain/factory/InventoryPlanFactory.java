package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryPlan;
import org.springframework.stereotype.Component;

/**
 * 库存-计划Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class InventoryPlanFactory {

	public InventoryPlan createInventoryPlan() {
		return new InventoryPlan();
	}

}