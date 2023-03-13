package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.InventoryMapHis;
import org.springframework.stereotype.Component;

/**
 * 移动平均价历史记录Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class InventoryMapHisFactory {

	public InventoryMapHis createInventoryMapHis() {
		return new InventoryMapHis();
	}

}