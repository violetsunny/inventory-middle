package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MaterialDocItemId;
import com.inventory.middle.domain.model.entity.MaterialDocItem;
import com.inventory.middle.domain.repository.MaterialDocItemRepository;
import com.inventory.middle.application.service.MaterialDocItemQueryService;
import com.inventory.middle.application.convertor.MaterialDocItemDtoConvertor;
import com.inventory.middle.client.dto.MaterialDocItemDto;
import com.inventory.middle.client.dto.query.MaterialDocItemPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证-itemQueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MaterialDocItemQueryServiceImpl implements MaterialDocItemQueryService {

	@Resource
	private MaterialDocItemRepository materialdocitemRepository;
	@Resource
	private MaterialDocItemDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MaterialDocItemDto> queryPage(MaterialDocItemPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MaterialDocItem> page = materialdocitemRepository.queryPage(pageQuery, params);
		List<MaterialDocItemDto> dtoList = page.getData().stream().map(dtoConvertor::fromMaterialDocItem).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MaterialDocItemDto findById(Long id) {
		return dtoConvertor.fromMaterialDocItem(materialdocitemRepository.findById(new MaterialDocItemId(id)));
	}

}

