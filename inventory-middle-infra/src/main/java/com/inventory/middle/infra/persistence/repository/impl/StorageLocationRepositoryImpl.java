package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.domain.repository.StorageLocationRepository;
import com.inventory.middle.infra.persistence.convertor.StorageLocationConvertor;
import com.inventory.middle.infra.persistence.entity.StorageLocationDo;
import com.inventory.middle.infra.persistence.mapper.StorageLocationMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.infra.dal.mybatis.util.PlusPageQuery;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 存储地点表Repository实现类
 */
@Repository
public class StorageLocationRepositoryImpl extends ServiceImpl<StorageLocationMapper, StorageLocationDo> implements StorageLocationRepository, IService<StorageLocationDo> {

    @Resource
    private StorageLocationConvertor convertor;

    @Override
    public PageResponse<StorageLocation> queryPage(PageQuery pageQuery, Map<String, Object> params) {
        IPage<StorageLocationDo> page = baseMapper.queryPage(new PlusPageQuery<StorageLocationDo>(pageQuery).getPage(params), params);
        List<StorageLocationDo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(page.getSize(), page.getCurrent());
        }
        List<StorageLocation> dtoList = records.stream().map(convertor::toStorageLocation).collect(Collectors.toList());
        return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public StorageLocation findById(StorageLocationId id) {
        StorageLocationDo doObj = baseMapper.findById(id.get());
        return convertor.toStorageLocation(doObj);
    }

    @Override
    public StorageLocation findByStorageLocationNo(String storageLocationNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("storageLocationNo", storageLocationNo);
        StorageLocationDo doObj = baseMapper.queryEntity(params);
        if (doObj == null) {
            return null;
        }
        return convertor.toStorageLocation(doObj);
    }

    @Override
    public List<StorageLocation> listByLogicalPlantId(Long logicalPlantId) {
        if (logicalPlantId == null) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("logicalPlantNo", logicalPlantId);
        List<StorageLocationDo> doList = baseMapper.queryList(params);
        if (CollectionUtils.isEmpty(doList)) {
            return Collections.emptyList();
        }
        return doList.stream().map(convertor::toStorageLocation).collect(Collectors.toList());
    }

    @Override
    public boolean store(StorageLocation storagelocation) {
        StorageLocationDo doObj = convertor.fromStorageLocation(storagelocation);
        return this.saveOrUpdate(doObj);
    }

    @Override
    public boolean update(StorageLocation storagelocation) {
        StorageLocationDo doObj = convertor.fromStorageLocation(storagelocation);
        return this.saveOrUpdate(doObj);
    }

    @Override
    public boolean delete(List<StorageLocationId> ids) {
        List<Long> tempIds = new ArrayList<>();
        ids.forEach(tempId -> tempIds.add(tempId.get()));
        return this.removeByIds(tempIds);
    }
}
