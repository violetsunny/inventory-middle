package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryTransitId;
import com.inventory.middle.domain.model.entity.InventoryTransit;
import com.inventory.middle.domain.repository.InventoryTransitRepository;
import com.inventory.middle.application.service.InventoryTransitQueryService;
import com.inventory.middle.application.convertor.InventoryTransitDtoConvertor;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.query.InventoryTransitPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存-在途QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class InventoryTransitQueryServiceImpl implements InventoryTransitQueryService {

	@Resource
	private InventoryTransitRepository inventorytransitRepository;
	@Resource
	private InventoryTransitDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryTransitDto> queryPage(InventoryTransitPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryTransit> page = inventorytransitRepository.queryPage(pageQuery, params);
		List<InventoryTransitDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryTransit).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryTransitDto findById(Long id) {
		return dtoConvertor.fromInventoryTransit(inventorytransitRepository.findById(new InventoryTransitId(id)));
	}

}

