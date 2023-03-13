package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.model.types.MaterialDocMainId;
import com.inventory.middle.domain.repository.MaterialDocMainRepository;
import com.inventory.middle.domain.specification.MaterialDocMainUpdateSpecification;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.application.convertor.MaterialDocMainDtoConvertor;
import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证主表ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MaterialDocMainApplicationServiceImpl implements MaterialDocMainApplicationService {

	@Resource
	private MaterialDocMainRepository materialdocmainRepository;

	@Resource
	private MaterialDocMainDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MaterialDocMainCommand materialdocmainCommand) {
		MaterialDocMain materialdocmain = dtoConvertor.toMaterialDocMain(materialdocmainCommand);
		return materialdocmainRepository.store(materialdocmain);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MaterialDocMainCommand materialdocmainCommand) {
		MaterialDocMain materialdocmain = dtoConvertor.toMaterialDocMain(materialdocmainCommand);
		MaterialDocMainUpdateSpecification materialdocmainUpdateSpecification = new MaterialDocMainUpdateSpecification();
		materialdocmainUpdateSpecification.isSatisfiedBy(materialdocmain);
		return  materialdocmainRepository.store(materialdocmain );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MaterialDocMainId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MaterialDocMainId(id));
		});
		return  materialdocmainRepository.delete(tempIds);
	}
}

