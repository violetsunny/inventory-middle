package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.model.types.InventoryAlertId;
import com.inventory.middle.domain.repository.InventoryAlertRepository;
import com.inventory.middle.domain.specification.InventoryAlertUpdateSpecification;
import com.inventory.middle.application.service.InventoryAlertApplicationService;
import com.inventory.middle.application.convertor.InventoryAlertDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryAlertCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存报警日志ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
@Service
@Slf4j
public class InventoryAlertApplicationServiceImpl implements InventoryAlertApplicationService {

	@Resource
	private InventoryAlertRepository inventoryalertRepository;

	@Resource
	private InventoryAlertDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryAlertCommand inventoryalertCommand) {
		InventoryAlert inventoryalert = dtoConvertor.toInventoryAlert(inventoryalertCommand);
		return inventoryalertRepository.store(inventoryalert);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryAlertCommand inventoryalertCommand) {
		InventoryAlert inventoryalert = dtoConvertor.toInventoryAlert(inventoryalertCommand);
		InventoryAlertUpdateSpecification inventoryalertUpdateSpecification = new InventoryAlertUpdateSpecification();
		inventoryalertUpdateSpecification.isSatisfiedBy(inventoryalert);
		return  inventoryalertRepository.store(inventoryalert );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryAlertId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryAlertId(id));
		});
		return  inventoryalertRepository.delete(tempIds);
	}
}

