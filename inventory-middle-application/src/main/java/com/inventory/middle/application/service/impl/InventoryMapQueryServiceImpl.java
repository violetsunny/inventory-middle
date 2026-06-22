package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryMapId;
import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.repository.InventoryMapRepository;
import com.inventory.middle.application.service.InventoryMapQueryService;
import com.inventory.middle.application.convertor.InventoryMapDtoConvertor;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.query.InventoryMapPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 移动平均价QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryMapQueryServiceImpl implements InventoryMapQueryService {

	@Resource
	private InventoryMapRepository inventorymapRepository;
	@Resource
	private InventoryMapDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryMapDto> queryPage(InventoryMapPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryMap> page = inventorymapRepository.queryPage(pageQuery, params);
		List<InventoryMapDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryMap).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryMapDto findById(Long id) {
		return dtoConvertor.fromInventoryMap(inventorymapRepository.findById(new InventoryMapId(id)));
	}

}

