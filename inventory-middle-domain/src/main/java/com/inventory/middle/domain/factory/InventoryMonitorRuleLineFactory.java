package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import org.springframework.stereotype.Component;

/**
 * 库存预警规则明细Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class InventoryMonitorRuleLineFactory {

	public InventoryMonitorRuleLine createInventoryMonitorRuleLine() {
		return new InventoryMonitorRuleLine();
	}

}