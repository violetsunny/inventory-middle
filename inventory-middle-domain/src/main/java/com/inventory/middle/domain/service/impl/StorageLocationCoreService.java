package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.domain.repository.StorageLocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存储地点服务实现
 * 迁移自: com.enn.inventory.center.core.service.impl.StorageLocationBizServiceImpl
 */
@Slf4j
@Service
public class StorageLocationCoreService {

    @Resource
    private StorageLocationRepository storageLocationRepository;

    public StorageLocationBO getByStorageLocationNo(String storageLocationNo) {
        StorageLocation entity = storageLocationRepository.findByStorageLocationNo(storageLocationNo);
        return toBO(entity);
    }

    public List<StorageLocationBO> listByPlantId(Long logicalPlantId) {
        List<StorageLocation> list = storageLocationRepository.listByLogicalPlantId(logicalPlantId);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().map(this::toBO).collect(Collectors.toList());
    }

    public StorageLocationBO getById(Long id) {
        if (id == null) {
            return null;
        }
        StorageLocation entity = storageLocationRepository.findById(new StorageLocationId(id));
        return toBO(entity);
    }

    private StorageLocationBO toBO(StorageLocation entity) {
        if (entity == null) {
            return null;
        }
        StorageLocationBO bo = new StorageLocationBO();
        BeanUtils.copyProperties(entity, bo);
        if (entity.getId() != null) {
            bo.setId(entity.getId().get());
        }
        return bo;
    }
}
