package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.MaterialLogicalPlantRef;
import com.inventory.middle.domain.repository.MaterialLogicalPlantRefRepository;
import com.inventory.middle.infra.persistence.convertor.MaterialLogicalPlantRefConvertor;
import com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo;
import com.inventory.middle.infra.persistence.entity.QueryMaterialLogicalPlantRefPO;
import com.inventory.middle.infra.persistence.mapper.MaterialLogicalPlantRefMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物料逻辑仓关联Repository实现类
 */
@Repository
public class MaterialLogicalPlantRefRepositoryImpl implements MaterialLogicalPlantRefRepository {

    @Resource
    private MaterialLogicalPlantRefMapper materialLogicalPlantRefMapper;

    @Resource
    private MaterialLogicalPlantRefConvertor materialLogicalPlantRefConvertor;

    @Override
    public MaterialLogicalPlantRef findById(Long id) {
        MaterialLogicalPlantRefDo doObj = materialLogicalPlantRefMapper.load(id);
        return toEntity(doObj);
    }

    @Override
    public boolean store(MaterialLogicalPlantRef entity) {
        MaterialLogicalPlantRefDo doObj = toDoObject(entity);
        return materialLogicalPlantRefMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(MaterialLogicalPlantRef entity) {
        MaterialLogicalPlantRefDo doObj = toDoObject(entity);
        return materialLogicalPlantRefMapper.update(doObj) > 0;
    }

    @Override
    public List<MaterialLogicalPlantRef> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    public List<MaterialLogicalPlantRef> getListByCondition(QueryMaterialLogicalPlantRefPO queryPO) {
        return materialLogicalPlantRefMapper.getListByCondition(queryPO)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<MaterialLogicalPlantRef> getListByMaterialCodes(String tenantId, List<String> materialCodes) {
        return materialLogicalPlantRefMapper.getListByMaterialCodes(tenantId, materialCodes)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    private MaterialLogicalPlantRefDo toDoObject(MaterialLogicalPlantRef entity) {
        return materialLogicalPlantRefConvertor.toDo(entity);
    }

    private MaterialLogicalPlantRef toEntity(MaterialLogicalPlantRefDo doObj) {
        if (doObj == null) return null;
        return materialLogicalPlantRefConvertor.toEntity(doObj);
    }
}
