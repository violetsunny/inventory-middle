package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryTransit;
import com.inventory.middle.domain.model.types.InventoryTransitId;
import com.inventory.middle.domain.repository.InventoryTransitRepository;
import com.inventory.middle.domain.specification.InventoryTransitUpdateSpecification;
import com.inventory.middle.application.service.InventoryTransitApplicationService;
import com.inventory.middle.application.convertor.InventoryTransitDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryTransitCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存-在途ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class InventoryTransitApplicationServiceImpl implements InventoryTransitApplicationService {

	@Resource
	private InventoryTransitRepository inventorytransitRepository;

	@Resource
	private InventoryTransitDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryTransitCommand inventorytransitCommand) {
		InventoryTransit inventorytransit = dtoConvertor.toInventoryTransit(inventorytransitCommand);
		return inventorytransitRepository.store(inventorytransit);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryTransitCommand inventorytransitCommand) {
		InventoryTransit inventorytransit = dtoConvertor.toInventoryTransit(inventorytransitCommand);
		InventoryTransitUpdateSpecification inventorytransitUpdateSpecification = new InventoryTransitUpdateSpecification();
		inventorytransitUpdateSpecification.isSatisfiedBy(inventorytransit);
		return  inventorytransitRepository.store(inventorytransit );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryTransitId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryTransitId(id));
		});
		return  inventorytransitRepository.delete(tempIds);
	}
}

