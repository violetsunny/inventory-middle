package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.application.service.InventorySnapshotQueryService;
import com.inventory.middle.application.convertor.InventorySnapshotDtoConvertor;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存快照-实时QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventorySnapshotQueryServiceImpl implements InventorySnapshotQueryService {

	@Resource
	private InventorySnapshotRepository inventorysnapshotRepository;
	@Resource
	private InventorySnapshotDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventorySnapshotDto> queryPage(InventorySnapshotPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventorySnapshot> page = inventorysnapshotRepository.queryPage(pageQuery, params);
		List<InventorySnapshotDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventorySnapshot).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventorySnapshotDto findById(Long id) {
		return dtoConvertor.fromInventorySnapshot(inventorysnapshotRepository.findById(new InventorySnapshotId(id)));
	}

}

