package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import com.inventory.middle.domain.model.types.MdocSubMaterialId;
import com.inventory.middle.domain.repository.MdocSubMaterialRepository;
import com.inventory.middle.domain.specification.MdocSubMaterialUpdateSpecification;
import com.inventory.middle.application.service.MdocSubMaterialApplicationService;
import com.inventory.middle.application.convertor.MdocSubMaterialDtoConvertor;
import com.inventory.middle.client.dto.command.MdocSubMaterialCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证子表-物料信息ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubMaterialApplicationServiceImpl implements MdocSubMaterialApplicationService {

	@Resource
	private MdocSubMaterialRepository mdocsubmaterialRepository;

	@Resource
	private MdocSubMaterialDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MdocSubMaterialCommand mdocsubmaterialCommand) {
		MdocSubMaterial mdocsubmaterial = dtoConvertor.toMdocSubMaterial(mdocsubmaterialCommand);
		return mdocsubmaterialRepository.store(mdocsubmaterial);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MdocSubMaterialCommand mdocsubmaterialCommand) {
		MdocSubMaterial mdocsubmaterial = dtoConvertor.toMdocSubMaterial(mdocsubmaterialCommand);
		MdocSubMaterialUpdateSpecification mdocsubmaterialUpdateSpecification = new MdocSubMaterialUpdateSpecification();
		mdocsubmaterialUpdateSpecification.isSatisfiedBy(mdocsubmaterial);
		return  mdocsubmaterialRepository.store(mdocsubmaterial );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MdocSubMaterialId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MdocSubMaterialId(id));
		});
		return  mdocsubmaterialRepository.delete(tempIds);
	}
}

