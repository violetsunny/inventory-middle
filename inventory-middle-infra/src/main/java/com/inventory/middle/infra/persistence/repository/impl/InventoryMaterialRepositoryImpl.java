package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.InventoryMaterial;
import com.inventory.middle.domain.repository.InventoryMaterialRepository;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialDo;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialQueryPO;
import com.inventory.middle.infra.persistence.entity.ListMaterialCodeParamPO;
import com.inventory.middle.infra.persistence.mapper.InventoryMaterialMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存物料Repository实现类
 */
@Repository
public class InventoryMaterialRepositoryImpl implements InventoryMaterialRepository {

    @Resource
    private InventoryMaterialMapper inventoryMaterialMapper;

    @Override
    public InventoryMaterial findById(Long id) {
        return null;
    }

    @Override
    public boolean store(InventoryMaterial entity) {
        InventoryMaterialDo doObj = toDoObject(entity);
        return inventoryMaterialMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(InventoryMaterial entity) {
        InventoryMaterialDo doObj = toDoObject(entity);
        return inventoryMaterialMapper.updateByMaterialCode(doObj) > 0;
    }

    @Override
    public List<InventoryMaterial> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    public List<InventoryMaterial> listByCondition(InventoryMaterialQueryPO queryPO) {
        return inventoryMaterialMapper.listByCondition(queryPO)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<InventoryMaterial> listByMaterialCodes(ListMaterialCodeParamPO param) {
        return inventoryMaterialMapper.listByMaterialCodeList(param)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * 按物料编码列表查询，返回原始 Do 对象（保留 outMaterialCode 字段，供 plan 迁移使用）
     */
    public List<InventoryMaterialDo> listByMaterialCodesRaw(ListMaterialCodeParamPO param) {
        return inventoryMaterialMapper.listByMaterialCodeList(param);
    }

    private InventoryMaterialDo toDoObject(InventoryMaterial entity) {
        InventoryMaterialDo doObj = new InventoryMaterialDo();
        BeanUtils.copyProperties(entity, doObj);
        return doObj;
    }

    private InventoryMaterial toEntity(InventoryMaterialDo doObj) {
        if (doObj == null) return null;
        InventoryMaterial entity = new InventoryMaterial();
        BeanUtils.copyProperties(doObj, entity);
        return entity;
    }
}
