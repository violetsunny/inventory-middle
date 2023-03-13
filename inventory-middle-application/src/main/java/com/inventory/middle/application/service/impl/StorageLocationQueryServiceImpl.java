package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.repository.StorageLocationRepository;
import com.inventory.middle.application.service.StorageLocationQueryService;
import com.inventory.middle.application.convertor.StorageLocationDtoConvertor;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.query.StorageLocationPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 存储地点表QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class StorageLocationQueryServiceImpl implements StorageLocationQueryService {

	@Resource
	private StorageLocationRepository storagelocationRepository;
	@Resource
	private StorageLocationDtoConvertor dtoConvertor;


	@Override
	public PageResponse<StorageLocationDto> queryPage(StorageLocationPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<StorageLocation> page = storagelocationRepository.queryPage(pageQuery, params);
		List<StorageLocationDto> dtoList = page.getData().stream().map(dtoConvertor::fromStorageLocation).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public StorageLocationDto findById(Long id) {
		return dtoConvertor.fromStorageLocation(storagelocationRepository.findById(new StorageLocationId(id)));
	}

}

