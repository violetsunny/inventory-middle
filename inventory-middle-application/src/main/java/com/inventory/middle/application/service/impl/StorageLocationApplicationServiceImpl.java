package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.domain.repository.StorageLocationRepository;
import com.inventory.middle.domain.specification.StorageLocationUpdateSpecification;
import com.inventory.middle.application.service.StorageLocationApplicationService;
import com.inventory.middle.application.convertor.StorageLocationDtoConvertor;
import com.inventory.middle.client.dto.command.StorageLocationCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储地点表ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class StorageLocationApplicationServiceImpl implements StorageLocationApplicationService {

	@Resource
	private StorageLocationRepository storagelocationRepository;

	@Resource
	private StorageLocationDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(StorageLocationCommand storagelocationCommand) {
		StorageLocation storagelocation = dtoConvertor.toStorageLocation(storagelocationCommand);
		return storagelocationRepository.store(storagelocation);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(StorageLocationCommand storagelocationCommand) {
		StorageLocation storagelocation = dtoConvertor.toStorageLocation(storagelocationCommand);
		StorageLocationUpdateSpecification storagelocationUpdateSpecification = new StorageLocationUpdateSpecification();
		storagelocationUpdateSpecification.isSatisfiedBy(storagelocation);
		return  storagelocationRepository.store(storagelocation );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<StorageLocationId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new StorageLocationId(id));
		});
		return  storagelocationRepository.delete(tempIds);
	}
}

