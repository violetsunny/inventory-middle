package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import com.inventory.middle.domain.repository.InventoryMonitorRuleLineRepository;
import com.inventory.middle.domain.specification.InventoryMonitorRuleLineUpdateSpecification;
import com.inventory.middle.application.service.InventoryMonitorRuleLineApplicationService;
import com.inventory.middle.application.convertor.InventoryMonitorRuleLineDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存预警规则明细ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryMonitorRuleLineApplicationServiceImpl implements InventoryMonitorRuleLineApplicationService {

	@Resource
	private InventoryMonitorRuleLineRepository inventorymonitorrulelineRepository;

	@Resource
	private InventoryMonitorRuleLineDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
		InventoryMonitorRuleLine inventorymonitorruleline = dtoConvertor.toInventoryMonitorRuleLine(inventorymonitorrulelineCommand);
		return inventorymonitorrulelineRepository.store(inventorymonitorruleline);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
		InventoryMonitorRuleLine inventorymonitorruleline = dtoConvertor.toInventoryMonitorRuleLine(inventorymonitorrulelineCommand);
		InventoryMonitorRuleLineUpdateSpecification inventorymonitorrulelineUpdateSpecification = new InventoryMonitorRuleLineUpdateSpecification();
		inventorymonitorrulelineUpdateSpecification.isSatisfiedBy(inventorymonitorruleline);
		return  inventorymonitorrulelineRepository.store(inventorymonitorruleline );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryMonitorRuleLineId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryMonitorRuleLineId(id));
		});
		return  inventorymonitorrulelineRepository.delete(tempIds);
	}
}

