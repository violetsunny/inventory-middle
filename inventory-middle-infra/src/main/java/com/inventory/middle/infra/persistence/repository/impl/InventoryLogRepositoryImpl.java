package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.InventoryLog;
import com.inventory.middle.domain.repository.InventoryLogRepository;
import com.inventory.middle.infra.persistence.entity.InventoryLogDo;
import com.inventory.middle.infra.persistence.entity.InventoryLogQueryDo;
import com.inventory.middle.infra.persistence.mapper.InventoryLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存操作日志Repository实现类
 */
@Repository
public class InventoryLogRepositoryImpl implements InventoryLogRepository {

    @Resource
    private InventoryLogMapper inventoryLogMapper;

    @Override
    public InventoryLog findById(Long id) {
        return null;
    }

    @Override
    public boolean store(InventoryLog entity) {
        InventoryLogDo doObj = toDoObject(entity);
        return inventoryLogMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(InventoryLog entity) {
        return false;
    }

    @Override
    public List<InventoryLog> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    public List<InventoryLog> list(InventoryLogQueryDo queryDo) {
        return inventoryLogMapper.list(queryDo)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    public boolean insertBatch(List<InventoryLog> entities) {
        List<InventoryLogDo> doList = entities.stream().map(this::toDoObject).collect(Collectors.toList());
        return inventoryLogMapper.insertBatch(doList) > 0;
    }

    private InventoryLogDo toDoObject(InventoryLog entity) {
        InventoryLogDo doObj = new InventoryLogDo();
        BeanUtils.copyProperties(entity, doObj);
        return doObj;
    }

    private InventoryLog toEntity(InventoryLogDo doObj) {
        if (doObj == null) return null;
        InventoryLog entity = new InventoryLog();
        BeanUtils.copyProperties(doObj, entity);
        return entity;
    }
}
