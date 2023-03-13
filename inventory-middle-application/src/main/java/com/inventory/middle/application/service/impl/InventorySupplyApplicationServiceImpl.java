package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.model.types.InventorySupplyId;
import com.inventory.middle.domain.repository.InventorySupplyRepository;
import com.inventory.middle.domain.specification.InventorySupplyUpdateSpecification;
import com.inventory.middle.application.service.InventorySupplyApplicationService;
import com.inventory.middle.application.convertor.InventorySupplyDtoConvertor;
import com.inventory.middle.client.dto.command.InventorySupplyCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存-供给ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class InventorySupplyApplicationServiceImpl implements InventorySupplyApplicationService {

	@Resource
	private InventorySupplyRepository inventorysupplyRepository;

	@Resource
	private InventorySupplyDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventorySupplyCommand inventorysupplyCommand) {
		InventorySupply inventorysupply = dtoConvertor.toInventorySupply(inventorysupplyCommand);
		return inventorysupplyRepository.store(inventorysupply);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventorySupplyCommand inventorysupplyCommand) {
		InventorySupply inventorysupply = dtoConvertor.toInventorySupply(inventorysupplyCommand);
		InventorySupplyUpdateSpecification inventorysupplyUpdateSpecification = new InventorySupplyUpdateSpecification();
		inventorysupplyUpdateSpecification.isSatisfiedBy(inventorysupply);
		return  inventorysupplyRepository.store(inventorysupply );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventorySupplyId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventorySupplyId(id));
		});
		return  inventorysupplyRepository.delete(tempIds);
	}
}

