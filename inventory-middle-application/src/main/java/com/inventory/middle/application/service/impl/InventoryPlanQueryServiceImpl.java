package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryPlanId;
import com.inventory.middle.domain.model.entity.InventoryPlan;
import com.inventory.middle.domain.repository.InventoryPlanRepository;
import com.inventory.middle.application.service.InventoryPlanQueryService;
import com.inventory.middle.application.convertor.InventoryPlanDtoConvertor;
import com.inventory.middle.client.dto.InventoryPlanDto;
import com.inventory.middle.client.dto.query.InventoryPlanPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存-计划QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class InventoryPlanQueryServiceImpl implements InventoryPlanQueryService {

	@Resource
	private InventoryPlanRepository inventoryplanRepository;
	@Resource
	private InventoryPlanDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryPlanDto> queryPage(InventoryPlanPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryPlan> page = inventoryplanRepository.queryPage(pageQuery, params);
		List<InventoryPlanDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryPlan).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryPlanDto findById(Long id) {
		return dtoConvertor.fromInventoryPlan(inventoryplanRepository.findById(new InventoryPlanId(id)));
	}

}

