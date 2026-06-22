package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MdocSubExt;
import com.inventory.middle.domain.model.types.MdocSubExtId;
import com.inventory.middle.domain.repository.MdocSubExtRepository;
import com.inventory.middle.domain.specification.MdocSubExtUpdateSpecification;
import com.inventory.middle.application.service.MdocSubExtApplicationService;
import com.inventory.middle.application.convertor.MdocSubExtDtoConvertor;
import com.inventory.middle.client.dto.command.MdocSubExtCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证-标签-扩展ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MdocSubExtApplicationServiceImpl implements MdocSubExtApplicationService {

	@Resource
	private MdocSubExtRepository mdocsubextRepository;

	@Resource
	private MdocSubExtDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MdocSubExtCommand mdocsubextCommand) {
		MdocSubExt mdocsubext = dtoConvertor.toMdocSubExt(mdocsubextCommand);
		return mdocsubextRepository.store(mdocsubext);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MdocSubExtCommand mdocsubextCommand) {
		MdocSubExt mdocsubext = dtoConvertor.toMdocSubExt(mdocsubextCommand);
		MdocSubExtUpdateSpecification mdocsubextUpdateSpecification = new MdocSubExtUpdateSpecification();
		mdocsubextUpdateSpecification.isSatisfiedBy(mdocsubext);
		return  mdocsubextRepository.store(mdocsubext );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MdocSubExtId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MdocSubExtId(id));
		});
		return  mdocsubextRepository.delete(tempIds);
	}
}

