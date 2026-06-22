package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.repository.InventoryMonitorRuleRepository;
import com.inventory.middle.application.service.InventoryMonitorRuleQueryService;
import com.inventory.middle.application.convertor.InventoryMonitorRuleDtoConvertor;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.query.InventoryMonitorRulePageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存预警规则QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryMonitorRuleQueryServiceImpl implements InventoryMonitorRuleQueryService {

	@Resource
	private InventoryMonitorRuleRepository inventorymonitorruleRepository;
	@Resource
	private InventoryMonitorRuleDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryMonitorRuleDto> queryPage(InventoryMonitorRulePageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryMonitorRule> page = inventorymonitorruleRepository.queryPage(pageQuery, params);
		List<InventoryMonitorRuleDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryMonitorRule).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryMonitorRuleDto findById(Long id) {
		return dtoConvertor.fromInventoryMonitorRule(inventorymonitorruleRepository.findById(new InventoryMonitorRuleId(id)));
	}

}

