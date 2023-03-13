package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MaterialDocMainId;
import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.repository.MaterialDocMainRepository;
import com.inventory.middle.application.service.MaterialDocMainQueryService;
import com.inventory.middle.application.convertor.MaterialDocMainDtoConvertor;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证主表QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MaterialDocMainQueryServiceImpl implements MaterialDocMainQueryService {

	@Resource
	private MaterialDocMainRepository materialdocmainRepository;
	@Resource
	private MaterialDocMainDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MaterialDocMainDto> queryPage(MaterialDocMainPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MaterialDocMain> page = materialdocmainRepository.queryPage(pageQuery, params);
		List<MaterialDocMainDto> dtoList = page.getData().stream().map(dtoConvertor::fromMaterialDocMain).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MaterialDocMainDto findById(Long id) {
		return dtoConvertor.fromMaterialDocMain(materialdocmainRepository.findById(new MaterialDocMainId(id)));
	}

}

