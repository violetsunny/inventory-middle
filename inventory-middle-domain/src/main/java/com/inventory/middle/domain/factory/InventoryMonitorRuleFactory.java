package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import org.springframework.stereotype.Component;

/**
 * 库存预警规则Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class InventoryMonitorRuleFactory {

	public InventoryMonitorRule createInventoryMonitorRule() {
		return new InventoryMonitorRule();
	}

}