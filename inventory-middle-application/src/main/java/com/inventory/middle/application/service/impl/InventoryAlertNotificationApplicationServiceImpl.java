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

import com.inventory.middle.infra.integration.push.RemoteUniformPushService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;

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

	@Resource
	private RemoteUniformPushService remoteUniformPushService;

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
		return inventoryalertnotificationRepository.delete(tempIds);
	}

	@Override
	@StopWatchWrapper(logHead = "AlertNotificationApp", msg = "sendAlertNotification")
	public void sendAlertNotification(com.inventory.middle.domain.model.bo.mq.InventoryAlertMessageBO alertMessageBO) {
		if (alertMessageBO == null) {
			log.warn("InventoryAlertNotificationApplicationServiceImpl.sendAlertNotification: null message, skip");
			return;
		}
		Map<String, Object> pushDTO = new HashMap<>();
		pushDTO.put("monitorRuleId", alertMessageBO.getMonitorRuleId());
		boolean pushed = remoteUniformPushService.push(pushDTO);
		log.info("预警通知推送结果: monitorRuleId={} pushed={}", alertMessageBO.getMonitorRuleId(), pushed);
	}
}

