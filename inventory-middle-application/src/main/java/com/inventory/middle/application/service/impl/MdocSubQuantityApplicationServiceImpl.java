package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import com.inventory.middle.domain.model.types.MdocSubQuantityId;
import com.inventory.middle.domain.repository.MdocSubQuantityRepository;
import com.inventory.middle.domain.specification.MdocSubQuantityUpdateSpecification;
import com.inventory.middle.application.service.MdocSubQuantityApplicationService;
import com.inventory.middle.application.convertor.MdocSubQuantityDtoConvertor;
import com.inventory.middle.client.dto.command.MdocSubQuantityCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证子表-数量ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubQuantityApplicationServiceImpl implements MdocSubQuantityApplicationService {

	@Resource
	private MdocSubQuantityRepository mdocsubquantityRepository;

	@Resource
	private MdocSubQuantityDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MdocSubQuantityCommand mdocsubquantityCommand) {
		MdocSubQuantity mdocsubquantity = dtoConvertor.toMdocSubQuantity(mdocsubquantityCommand);
		return mdocsubquantityRepository.store(mdocsubquantity);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MdocSubQuantityCommand mdocsubquantityCommand) {
		MdocSubQuantity mdocsubquantity = dtoConvertor.toMdocSubQuantity(mdocsubquantityCommand);
		MdocSubQuantityUpdateSpecification mdocsubquantityUpdateSpecification = new MdocSubQuantityUpdateSpecification();
		mdocsubquantityUpdateSpecification.isSatisfiedBy(mdocsubquantity);
		return  mdocsubquantityRepository.store(mdocsubquantity );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MdocSubQuantityId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MdocSubQuantityId(id));
		});
		return  mdocsubquantityRepository.delete(tempIds);
	}
}

