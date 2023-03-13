package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryMap;
import org.springframework.stereotype.Component;

/**
 * 移动平均价Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class InventoryMapFactory {

	public InventoryMap createInventoryMap() {
		return new InventoryMap();
	}

}