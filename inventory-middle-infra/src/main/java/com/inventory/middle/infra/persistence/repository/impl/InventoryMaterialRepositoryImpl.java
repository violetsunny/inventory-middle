package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.InventoryMaterial;
import com.inventory.middle.domain.repository.InventoryMaterialQueryParam;
import com.inventory.middle.domain.repository.InventoryMaterialRepository;
import com.inventory.middle.domain.repository.ListMaterialCodeQueryParam;
import com.inventory.middle.infra.persistence.convertor.InventoryMaterialConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialDo;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialQueryPO;
import com.inventory.middle.infra.persistence.entity.ListMaterialCodeParamPO;
import com.inventory.middle.infra.persistence.mapper.InventoryMaterialMapper;
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

    @Resource
    private InventoryMaterialConvertor inventoryMaterialConvertor;

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

    @Override
    public List<InventoryMaterial> listByCondition(InventoryMaterialQueryParam queryParam) {
        InventoryMaterialQueryPO queryPO = toQueryPO(queryParam);
        return inventoryMaterialMapper.listByCondition(queryPO)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<InventoryMaterial> listByMaterialCodes(ListMaterialCodeQueryParam queryParam) {
        ListMaterialCodeParamPO param = toListMaterialCodeParamPO(queryParam);
        return inventoryMaterialMapper.listByMaterialCodeList(param)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    private InventoryMaterialQueryPO toQueryPO(InventoryMaterialQueryParam queryParam) {
        return inventoryMaterialConvertor.toQueryPO(queryParam);
    }

    private ListMaterialCodeParamPO toListMaterialCodeParamPO(ListMaterialCodeQueryParam queryParam) {
        return inventoryMaterialConvertor.toListMaterialCodeParamPO(queryParam);
    }

    private InventoryMaterialDo toDoObject(InventoryMaterial entity) {
        return inventoryMaterialConvertor.toDo(entity);
    }

    private InventoryMaterial toEntity(InventoryMaterialDo doObj) {
        if (doObj == null) return null;
        return inventoryMaterialConvertor.toEntity(doObj);
    }
}
