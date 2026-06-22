package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.repository.InventoryMonitorRuleLineRepository;
import com.inventory.middle.application.service.InventoryMonitorRuleLineQueryService;
import com.inventory.middle.application.convertor.InventoryMonitorRuleLineDtoConvertor;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存预警规则明细QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryMonitorRuleLineQueryServiceImpl implements InventoryMonitorRuleLineQueryService {

	@Resource
	private InventoryMonitorRuleLineRepository inventorymonitorrulelineRepository;
	@Resource
	private InventoryMonitorRuleLineDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryMonitorRuleLineDto> queryPage(InventoryMonitorRuleLinePageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryMonitorRuleLine> page = inventorymonitorrulelineRepository.queryPage(pageQuery, params);
		List<InventoryMonitorRuleLineDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryMonitorRuleLine).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryMonitorRuleLineDto findById(Long id) {
		return dtoConvertor.fromInventoryMonitorRuleLine(inventorymonitorrulelineRepository.findById(new InventoryMonitorRuleLineId(id)));
	}

}

