package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.domain.specification.InventorySnapshotUpdateSpecification;
import com.inventory.middle.application.service.InventorySnapshotApplicationService;
import com.inventory.middle.application.convertor.InventorySnapshotDtoConvertor;
import com.inventory.middle.client.dto.command.InventorySnapshotCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存快照-实时ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventorySnapshotApplicationServiceImpl implements InventorySnapshotApplicationService {

	@Resource
	private InventorySnapshotRepository inventorysnapshotRepository;

	@Resource
	private InventorySnapshotDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventorySnapshotCommand inventorysnapshotCommand) {
		InventorySnapshot inventorysnapshot = dtoConvertor.toInventorySnapshot(inventorysnapshotCommand);
		return inventorysnapshotRepository.store(inventorysnapshot);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventorySnapshotCommand inventorysnapshotCommand) {
		InventorySnapshot inventorysnapshot = dtoConvertor.toInventorySnapshot(inventorysnapshotCommand);
		InventorySnapshotUpdateSpecification inventorysnapshotUpdateSpecification = new InventorySnapshotUpdateSpecification();
		inventorysnapshotUpdateSpecification.isSatisfiedBy(inventorysnapshot);
		return  inventorysnapshotRepository.store(inventorysnapshot );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventorySnapshotId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventorySnapshotId(id));
		});
		return  inventorysnapshotRepository.delete(tempIds);
	}
}

