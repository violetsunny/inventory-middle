package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.Shipment;
import com.inventory.middle.domain.model.types.ShipmentId;
import com.inventory.middle.domain.repository.ShipmentRepository;
import com.inventory.middle.domain.specification.ShipmentUpdateSpecification;
import com.inventory.middle.application.service.ShipmentApplicationService;
import com.inventory.middle.application.convertor.ShipmentDtoConvertor;
import com.inventory.middle.client.dto.command.ShipmentCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 交运单ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class ShipmentApplicationServiceImpl implements ShipmentApplicationService {

	@Resource
	private ShipmentRepository shipmentRepository;

	@Resource
	private ShipmentDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(ShipmentCommand shipmentCommand) {
		Shipment shipment = dtoConvertor.toShipment(shipmentCommand);
		return shipmentRepository.store(shipment);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(ShipmentCommand shipmentCommand) {
		Shipment shipment = dtoConvertor.toShipment(shipmentCommand);
		ShipmentUpdateSpecification shipmentUpdateSpecification = new ShipmentUpdateSpecification();
		shipmentUpdateSpecification.isSatisfiedBy(shipment);
		return  shipmentRepository.store(shipment );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<ShipmentId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new ShipmentId(id));
		});
		return  shipmentRepository.delete(tempIds);
	}
}

