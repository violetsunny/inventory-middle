package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryPlan;
import com.inventory.middle.domain.model.types.InventoryPlanId;
import com.inventory.middle.domain.repository.InventoryPlanRepository;
import com.inventory.middle.domain.specification.InventoryPlanUpdateSpecification;
import com.inventory.middle.application.service.InventoryPlanApplicationService;
import com.inventory.middle.application.convertor.InventoryPlanDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryPlanCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存-计划ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class InventoryPlanApplicationServiceImpl implements InventoryPlanApplicationService {

	@Resource
	private InventoryPlanRepository inventoryplanRepository;

	@Resource
	private InventoryPlanDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryPlanCommand inventoryplanCommand) {
		InventoryPlan inventoryplan = dtoConvertor.toInventoryPlan(inventoryplanCommand);
		return inventoryplanRepository.store(inventoryplan);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryPlanCommand inventoryplanCommand) {
		InventoryPlan inventoryplan = dtoConvertor.toInventoryPlan(inventoryplanCommand);
		InventoryPlanUpdateSpecification inventoryplanUpdateSpecification = new InventoryPlanUpdateSpecification();
		inventoryplanUpdateSpecification.isSatisfiedBy(inventoryplan);
		return  inventoryplanRepository.store(inventoryplan );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryPlanId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryPlanId(id));
		});
		return  inventoryplanRepository.delete(tempIds);
	}
}

