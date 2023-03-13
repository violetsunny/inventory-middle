package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryDemandId;
import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.repository.InventoryDemandRepository;
import com.inventory.middle.application.service.InventoryDemandQueryService;
import com.inventory.middle.application.convertor.InventoryDemandDtoConvertor;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存-需求QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryDemandQueryServiceImpl implements InventoryDemandQueryService {

	@Resource
	private InventoryDemandRepository inventorydemandRepository;
	@Resource
	private InventoryDemandDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryDemandDto> queryPage(InventoryDemandPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryDemand> page = inventorydemandRepository.queryPage(pageQuery, params);
		List<InventoryDemandDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryDemand).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryDemandDto findById(Long id) {
		return dtoConvertor.fromInventoryDemand(inventorydemandRepository.findById(new InventoryDemandId(id)));
	}

}

