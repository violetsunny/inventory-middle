package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import com.inventory.middle.domain.repository.InventoryAlertNotificationRepository;
import com.inventory.middle.domain.specification.InventoryAlertNotificationUpdateSpecification;
import com.inventory.middle.application.service.InventoryAlertNotificationApplicationService;
import com.inventory.middle.application.convertor.InventoryAlertNotificationDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryAlertNotificationCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存报警通知记录ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryAlertNotificationApplicationServiceImpl implements InventoryAlertNotificationApplicationService {

	@Resource
	private InventoryAlertNotificationRepository inventoryalertnotificationRepository;

	@Resource
	private InventoryAlertNotificationDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryAlertNotificationCommand inventoryalertnotificationCommand) {
		InventoryAlertNotification inventoryalertnotification = dtoConvertor.toInventoryAlertNotification(inventoryalertnotificationCommand);
		return inventoryalertnotificationRepository.store(inventoryalertnotification);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryAlertNotificationCommand inventoryalertnotificationCommand) {
		InventoryAlertNotification inventoryalertnotification = dtoConvertor.toInventoryAlertNotification(inventoryalertnotificationCommand);
		InventoryAlertNotificationUpdateSpecification inventoryalertnotificationUpdateSpecification = new InventoryAlertNotificationUpdateSpecification();
		inventoryalertnotificationUpdateSpecification.isSatisfiedBy(inventoryalertnotification);
		return  inventoryalertnotificationRepository.store(inventoryalertnotification );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryAlertNotificationId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryAlertNotificationId(id));
		});
		return  inventoryalertnotificationRepository.delete(tempIds);
	}
}

