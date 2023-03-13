package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MdocSubMap;
import com.inventory.middle.domain.model.types.MdocSubMapId;
import com.inventory.middle.domain.repository.MdocSubMapRepository;
import com.inventory.middle.domain.specification.MdocSubMapUpdateSpecification;
import com.inventory.middle.application.service.MdocSubMapApplicationService;
import com.inventory.middle.application.convertor.MdocSubMapDtoConvertor;
import com.inventory.middle.client.dto.command.MdocSubMapCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证-标签-移动平均价ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubMapApplicationServiceImpl implements MdocSubMapApplicationService {

	@Resource
	private MdocSubMapRepository mdocsubmapRepository;

	@Resource
	private MdocSubMapDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MdocSubMapCommand mdocsubmapCommand) {
		MdocSubMap mdocsubmap = dtoConvertor.toMdocSubMap(mdocsubmapCommand);
		return mdocsubmapRepository.store(mdocsubmap);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MdocSubMapCommand mdocsubmapCommand) {
		MdocSubMap mdocsubmap = dtoConvertor.toMdocSubMap(mdocsubmapCommand);
		MdocSubMapUpdateSpecification mdocsubmapUpdateSpecification = new MdocSubMapUpdateSpecification();
		mdocsubmapUpdateSpecification.isSatisfiedBy(mdocsubmap);
		return  mdocsubmapRepository.store(mdocsubmap );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MdocSubMapId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MdocSubMapId(id));
		});
		return  mdocsubmapRepository.delete(tempIds);
	}
}

