package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MdocSubInOut;
import com.inventory.middle.domain.model.types.MdocSubInOutId;
import com.inventory.middle.domain.repository.MdocSubInOutRepository;
import com.inventory.middle.domain.specification.MdocSubInOutUpdateSpecification;
import com.inventory.middle.application.service.MdocSubInOutApplicationService;
import com.inventory.middle.application.convertor.MdocSubInOutDtoConvertor;
import com.inventory.middle.client.dto.command.MdocSubInOutCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证子表-出入库信息ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubInOutApplicationServiceImpl implements MdocSubInOutApplicationService {

	@Resource
	private MdocSubInOutRepository mdocsubinoutRepository;

	@Resource
	private MdocSubInOutDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MdocSubInOutCommand mdocsubinoutCommand) {
		MdocSubInOut mdocsubinout = dtoConvertor.toMdocSubInOut(mdocsubinoutCommand);
		return mdocsubinoutRepository.store(mdocsubinout);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MdocSubInOutCommand mdocsubinoutCommand) {
		MdocSubInOut mdocsubinout = dtoConvertor.toMdocSubInOut(mdocsubinoutCommand);
		MdocSubInOutUpdateSpecification mdocsubinoutUpdateSpecification = new MdocSubInOutUpdateSpecification();
		mdocsubinoutUpdateSpecification.isSatisfiedBy(mdocsubinout);
		return  mdocsubinoutRepository.store(mdocsubinout );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MdocSubInOutId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MdocSubInOutId(id));
		});
		return  mdocsubinoutRepository.delete(tempIds);
	}
}

