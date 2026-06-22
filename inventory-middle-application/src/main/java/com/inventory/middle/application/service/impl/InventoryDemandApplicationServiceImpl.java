package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.model.types.InventoryDemandId;
import com.inventory.middle.domain.repository.InventoryDemandRepository;
import com.inventory.middle.domain.specification.InventoryDemandUpdateSpecification;
import com.inventory.middle.application.service.InventoryDemandApplicationService;
import com.inventory.middle.application.convertor.InventoryDemandDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryDemandCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存-需求ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryDemandApplicationServiceImpl implements InventoryDemandApplicationService {

	@Resource
	private InventoryDemandRepository inventorydemandRepository;

	@Resource
	private InventoryDemandDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryDemandCommand inventorydemandCommand) {
		InventoryDemand inventorydemand = dtoConvertor.toInventoryDemand(inventorydemandCommand);
		return inventorydemandRepository.store(inventorydemand);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryDemandCommand inventorydemandCommand) {
		InventoryDemand inventorydemand = dtoConvertor.toInventoryDemand(inventorydemandCommand);
		InventoryDemandUpdateSpecification inventorydemandUpdateSpecification = new InventoryDemandUpdateSpecification();
		inventorydemandUpdateSpecification.isSatisfiedBy(inventorydemand);
		return  inventorydemandRepository.store(inventorydemand );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryDemandId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryDemandId(id));
		});
		return  inventorydemandRepository.delete(tempIds);
	}
}

