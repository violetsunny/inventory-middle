package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.ShipmentLine;
import com.inventory.middle.domain.model.types.ShipmentLineId;
import com.inventory.middle.domain.repository.ShipmentLineRepository;
import com.inventory.middle.domain.specification.ShipmentLineUpdateSpecification;
import com.inventory.middle.application.service.ShipmentLineApplicationService;
import com.inventory.middle.application.convertor.ShipmentLineDtoConvertor;
import com.inventory.middle.client.dto.command.ShipmentLineCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 交运单明细ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
@Service
@Slf4j
public class ShipmentLineApplicationServiceImpl implements ShipmentLineApplicationService {

	@Resource
	private ShipmentLineRepository shipmentlineRepository;

	@Resource
	private ShipmentLineDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(ShipmentLineCommand shipmentlineCommand) {
		ShipmentLine shipmentline = dtoConvertor.toShipmentLine(shipmentlineCommand);
		return shipmentlineRepository.store(shipmentline);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(ShipmentLineCommand shipmentlineCommand) {
		ShipmentLine shipmentline = dtoConvertor.toShipmentLine(shipmentlineCommand);
		ShipmentLineUpdateSpecification shipmentlineUpdateSpecification = new ShipmentLineUpdateSpecification();
		shipmentlineUpdateSpecification.isSatisfiedBy(shipmentline);
		return  shipmentlineRepository.store(shipmentline );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<ShipmentLineId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new ShipmentLineId(id));
		});
		return  shipmentlineRepository.delete(tempIds);
	}
}

