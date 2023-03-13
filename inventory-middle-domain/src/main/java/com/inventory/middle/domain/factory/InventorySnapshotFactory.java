package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventorySnapshot;
import org.springframework.stereotype.Component;

/**
 * 库存快照-实时Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class InventorySnapshotFactory {

	public InventorySnapshot createInventorySnapshot() {
		return new InventorySnapshot();
	}

}