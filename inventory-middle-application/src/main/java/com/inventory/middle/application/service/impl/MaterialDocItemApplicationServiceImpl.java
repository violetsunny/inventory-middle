package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MaterialDocItem;
import com.inventory.middle.domain.model.types.MaterialDocItemId;
import com.inventory.middle.domain.repository.MaterialDocItemRepository;
import com.inventory.middle.domain.specification.MaterialDocItemUpdateSpecification;
import com.inventory.middle.application.service.MaterialDocItemApplicationService;
import com.inventory.middle.application.convertor.MaterialDocItemDtoConvertor;
import com.inventory.middle.client.dto.command.MaterialDocItemCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证-itemApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MaterialDocItemApplicationServiceImpl implements MaterialDocItemApplicationService {

	@Resource
	private MaterialDocItemRepository materialdocitemRepository;

	@Resource
	private MaterialDocItemDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MaterialDocItemCommand materialdocitemCommand) {
		MaterialDocItem materialdocitem = dtoConvertor.toMaterialDocItem(materialdocitemCommand);
		return materialdocitemRepository.store(materialdocitem);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MaterialDocItemCommand materialdocitemCommand) {
		MaterialDocItem materialdocitem = dtoConvertor.toMaterialDocItem(materialdocitemCommand);
		MaterialDocItemUpdateSpecification materialdocitemUpdateSpecification = new MaterialDocItemUpdateSpecification();
		materialdocitemUpdateSpecification.isSatisfiedBy(materialdocitem);
		return  materialdocitemRepository.store(materialdocitem );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MaterialDocItemId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MaterialDocItemId(id));
		});
		return  materialdocitemRepository.delete(tempIds);
	}
}

