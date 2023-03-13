package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import com.inventory.middle.domain.repository.LogicalPlantRepository;
import com.inventory.middle.domain.specification.LogicalPlantUpdateSpecification;
import com.inventory.middle.application.service.LogicalPlantApplicationService;
import com.inventory.middle.application.convertor.LogicalPlantDtoConvertor;
import com.inventory.middle.client.dto.command.LogicalPlantCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑仓库表ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class LogicalPlantApplicationServiceImpl implements LogicalPlantApplicationService {

	@Resource
	private LogicalPlantRepository logicalplantRepository;

	@Resource
	private LogicalPlantDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(LogicalPlantCommand logicalplantCommand) {
		LogicalPlant logicalplant = dtoConvertor.toLogicalPlant(logicalplantCommand);
		return logicalplantRepository.store(logicalplant);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(LogicalPlantCommand logicalplantCommand) {
		LogicalPlant logicalplant = dtoConvertor.toLogicalPlant(logicalplantCommand);
		LogicalPlantUpdateSpecification logicalplantUpdateSpecification = new LogicalPlantUpdateSpecification();
		logicalplantUpdateSpecification.isSatisfiedBy(logicalplant);
		return  logicalplantRepository.store(logicalplant );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<LogicalPlantId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new LogicalPlantId(id));
		});
		return  logicalplantRepository.delete(tempIds);
	}
}

