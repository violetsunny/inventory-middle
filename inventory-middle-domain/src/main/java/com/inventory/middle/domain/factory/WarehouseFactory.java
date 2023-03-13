package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.Warehouse;
import org.springframework.stereotype.Component;

/**
 * 物理仓库表Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class WarehouseFactory {

	public Warehouse createWarehouse() {
		return new Warehouse();
	}

}