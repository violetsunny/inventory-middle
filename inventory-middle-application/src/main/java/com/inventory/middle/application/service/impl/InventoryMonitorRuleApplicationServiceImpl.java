package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import com.inventory.middle.domain.repository.InventoryMonitorRuleRepository;
import com.inventory.middle.domain.specification.InventoryMonitorRuleUpdateSpecification;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.application.convertor.InventoryMonitorRuleDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存预警规则ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryMonitorRuleApplicationServiceImpl implements InventoryMonitorRuleApplicationService {

	@Resource
	private InventoryMonitorRuleRepository inventorymonitorruleRepository;

	@Resource
	private InventoryMonitorRuleDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryMonitorRuleCommand inventorymonitorruleCommand) {
		InventoryMonitorRule inventorymonitorrule = dtoConvertor.toInventoryMonitorRule(inventorymonitorruleCommand);
		return inventorymonitorruleRepository.store(inventorymonitorrule);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryMonitorRuleCommand inventorymonitorruleCommand) {
		InventoryMonitorRule inventorymonitorrule = dtoConvertor.toInventoryMonitorRule(inventorymonitorruleCommand);
		InventoryMonitorRuleUpdateSpecification inventorymonitorruleUpdateSpecification = new InventoryMonitorRuleUpdateSpecification();
		inventorymonitorruleUpdateSpecification.isSatisfiedBy(inventorymonitorrule);
		return  inventorymonitorruleRepository.store(inventorymonitorrule );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryMonitorRuleId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryMonitorRuleId(id));
		});
		return  inventorymonitorruleRepository.delete(tempIds);
	}
}

