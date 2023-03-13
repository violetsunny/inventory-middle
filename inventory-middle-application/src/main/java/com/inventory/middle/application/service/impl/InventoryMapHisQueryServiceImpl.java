package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryMapHisId;
import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.repository.InventoryMapHisRepository;
import com.inventory.middle.application.service.InventoryMapHisQueryService;
import com.inventory.middle.application.convertor.InventoryMapHisDtoConvertor;
import com.inventory.middle.client.dto.InventoryMapHisDto;
import com.inventory.middle.client.dto.query.InventoryMapHisPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 移动平均价历史记录QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryMapHisQueryServiceImpl implements InventoryMapHisQueryService {

	@Resource
	private InventoryMapHisRepository inventorymaphisRepository;
	@Resource
	private InventoryMapHisDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryMapHisDto> queryPage(InventoryMapHisPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryMapHis> page = inventorymaphisRepository.queryPage(pageQuery, params);
		List<InventoryMapHisDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryMapHis).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryMapHisDto findById(Long id) {
		return dtoConvertor.fromInventoryMapHis(inventorymaphisRepository.findById(new InventoryMapHisId(id)));
	}

}

