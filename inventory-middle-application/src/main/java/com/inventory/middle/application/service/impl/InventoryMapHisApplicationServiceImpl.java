package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.model.types.InventoryMapHisId;
import com.inventory.middle.domain.repository.InventoryMapHisRepository;
import com.inventory.middle.domain.specification.InventoryMapHisUpdateSpecification;
import com.inventory.middle.application.service.InventoryMapHisApplicationService;
import com.inventory.middle.application.convertor.InventoryMapHisDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryMapHisCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 移动平均价历史记录ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryMapHisApplicationServiceImpl implements InventoryMapHisApplicationService {

	@Resource
	private InventoryMapHisRepository inventorymaphisRepository;

	@Resource
	private InventoryMapHisDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryMapHisCommand inventorymaphisCommand) {
		InventoryMapHis inventorymaphis = dtoConvertor.toInventoryMapHis(inventorymaphisCommand);
		return inventorymaphisRepository.store(inventorymaphis);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryMapHisCommand inventorymaphisCommand) {
		InventoryMapHis inventorymaphis = dtoConvertor.toInventoryMapHis(inventorymaphisCommand);
		InventoryMapHisUpdateSpecification inventorymaphisUpdateSpecification = new InventoryMapHisUpdateSpecification();
		inventorymaphisUpdateSpecification.isSatisfiedBy(inventorymaphis);
		return  inventorymaphisRepository.store(inventorymaphis );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryMapHisId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryMapHisId(id));
		});
		return  inventorymaphisRepository.delete(tempIds);
	}
}

