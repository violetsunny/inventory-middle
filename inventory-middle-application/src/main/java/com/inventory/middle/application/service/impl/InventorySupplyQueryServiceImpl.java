package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventorySupplyId;
import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.repository.InventorySupplyRepository;
import com.inventory.middle.application.service.InventorySupplyQueryService;
import com.inventory.middle.application.convertor.InventorySupplyDtoConvertor;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存-供给QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class InventorySupplyQueryServiceImpl implements InventorySupplyQueryService {

	@Resource
	private InventorySupplyRepository inventorysupplyRepository;
	@Resource
	private InventorySupplyDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventorySupplyDto> queryPage(InventorySupplyPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventorySupply> page = inventorysupplyRepository.queryPage(pageQuery, params);
		List<InventorySupplyDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventorySupply).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventorySupplyDto findById(Long id) {
		return dtoConvertor.fromInventorySupply(inventorysupplyRepository.findById(new InventorySupplyId(id)));
	}

}

