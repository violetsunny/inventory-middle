package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.WarehouseId;
import com.inventory.middle.domain.model.entity.Warehouse;
import com.inventory.middle.domain.repository.WarehouseRepository;
import com.inventory.middle.application.service.WarehouseQueryService;
import com.inventory.middle.application.convertor.WarehouseDtoConvertor;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.query.WarehousePageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物理仓库表QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class WarehouseQueryServiceImpl implements WarehouseQueryService {

	@Resource
	private WarehouseRepository warehouseRepository;
	@Resource
	private WarehouseDtoConvertor dtoConvertor;


	@Override
	public PageResponse<WarehouseDto> queryPage(WarehousePageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<Warehouse> page = warehouseRepository.queryPage(pageQuery, params);
		List<WarehouseDto> dtoList = page.getData().stream().map(dtoConvertor::fromWarehouse).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public WarehouseDto findById(Long id) {
		return dtoConvertor.fromWarehouse(warehouseRepository.findById(new WarehouseId(id)));
	}

}

