package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MdocSubMaterialId;
import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import com.inventory.middle.domain.repository.MdocSubMaterialRepository;
import com.inventory.middle.application.service.MdocSubMaterialQueryService;
import com.inventory.middle.application.convertor.MdocSubMaterialDtoConvertor;
import com.inventory.middle.client.dto.MdocSubMaterialDto;
import com.inventory.middle.client.dto.query.MdocSubMaterialPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证子表-物料信息QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubMaterialQueryServiceImpl implements MdocSubMaterialQueryService {

	@Resource
	private MdocSubMaterialRepository mdocsubmaterialRepository;
	@Resource
	private MdocSubMaterialDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MdocSubMaterialDto> queryPage(MdocSubMaterialPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MdocSubMaterial> page = mdocsubmaterialRepository.queryPage(pageQuery, params);
		List<MdocSubMaterialDto> dtoList = page.getData().stream().map(dtoConvertor::fromMdocSubMaterial).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MdocSubMaterialDto findById(Long id) {
		return dtoConvertor.fromMdocSubMaterial(mdocsubmaterialRepository.findById(new MdocSubMaterialId(id)));
	}

}

