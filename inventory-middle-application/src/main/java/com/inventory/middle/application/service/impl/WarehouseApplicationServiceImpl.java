package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.Warehouse;
import com.inventory.middle.domain.model.types.WarehouseId;
import com.inventory.middle.domain.repository.WarehouseRepository;
import com.inventory.middle.domain.specification.WarehouseUpdateSpecification;
import com.inventory.middle.application.service.WarehouseApplicationService;
import com.inventory.middle.application.convertor.WarehouseDtoConvertor;
import com.inventory.middle.client.dto.command.WarehouseCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物理仓库表ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class WarehouseApplicationServiceImpl implements WarehouseApplicationService {

	@Resource
	private WarehouseRepository warehouseRepository;

	@Resource
	private WarehouseDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(WarehouseCommand warehouseCommand) {
		Warehouse warehouse = dtoConvertor.toWarehouse(warehouseCommand);
		return warehouseRepository.store(warehouse);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(WarehouseCommand warehouseCommand) {
		Warehouse warehouse = dtoConvertor.toWarehouse(warehouseCommand);
		WarehouseUpdateSpecification warehouseUpdateSpecification = new WarehouseUpdateSpecification();
		warehouseUpdateSpecification.isSatisfiedBy(warehouse);
		return  warehouseRepository.store(warehouse );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<WarehouseId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new WarehouseId(id));
		});
		return  warehouseRepository.delete(tempIds);
	}
}

