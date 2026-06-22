package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.Shipment;
import org.springframework.stereotype.Component;

/**
 * 交运单Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class ShipmentFactory {

	public Shipment createShipment() {
		return new Shipment();
	}

}