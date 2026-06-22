package com.inventory.middle.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;
import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.types.InventoryMapId;
import com.inventory.middle.domain.repository.InventoryMapRepository;
import com.inventory.middle.domain.service.map.InventoryMapDomainService;
import com.inventory.middle.domain.specification.InventoryMapUpdateSpecification;
import com.inventory.middle.application.service.InventoryMapApplicationService;
import com.inventory.middle.application.convertor.InventoryMapDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryMapCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;

/**
 * 移动平均价ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryMapApplicationServiceImpl implements InventoryMapApplicationService {

	@Resource
	private InventoryMapRepository inventorymapRepository;

	@Resource
	private InventoryMapDtoConvertor dtoConvertor;

	@Resource
	private InventoryMapDomainService inventoryMapDomainService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryMapCommand inventorymapCommand) {
		InventoryMap inventorymap = dtoConvertor.toInventoryMap(inventorymapCommand);
		return inventorymapRepository.store(inventorymap);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryMapCommand inventorymapCommand) {
		InventoryMap inventorymap = dtoConvertor.toInventoryMap(inventorymapCommand);
		InventoryMapUpdateSpecification inventorymapUpdateSpecification = new InventoryMapUpdateSpecification();
		inventorymapUpdateSpecification.isSatisfiedBy(inventorymap);
		return inventorymapRepository.store(inventorymap);
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryMapId> tempIds = new ArrayList<>();
		ids.forEach(id -> tempIds.add(new InventoryMapId(id)));
		return inventorymapRepository.delete(tempIds);
	}

	@Override
	@StopWatchWrapper(logHead = "InventoryMapApp", msg = "calFromMessage")
	public void calFromMessage(InventoryChangeMessage message) {
		log.info("InventoryMapApplicationServiceImpl.calFromMessage message={}", JSON.toJSONString(message));
		try {
			inventoryMapDomainService.cal(message);
		} catch (Exception e) {
			log.error("InventoryMapApplicationServiceImpl.calFromMessage failed", e);
			throw new RuntimeException(e);
		}
	}
}

