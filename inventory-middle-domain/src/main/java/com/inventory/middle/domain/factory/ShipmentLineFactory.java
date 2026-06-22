package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.ShipmentLine;
import org.springframework.stereotype.Component;

/**
 * 交运单明细Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
@Component
public class ShipmentLineFactory {

	public ShipmentLine createShipmentLine() {
		return new ShipmentLine();
	}

}