package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 存储地点表Repository
 */
public interface StorageLocationRepository {

    PageResponse<StorageLocation> queryPage(PageQuery pageQuery, Map<String, Object> params);

    StorageLocation findById(StorageLocationId id);

    /** 按存储地点编码查询 */
    StorageLocation findByStorageLocationNo(String storageLocationNo);

    /** 按逻辑仓库ID查询列表 */
    List<StorageLocation> listByLogicalPlantId(Long logicalPlantId);

    boolean store(StorageLocation storagelocation);

    boolean update(StorageLocation storagelocation);

    boolean delete(List<StorageLocationId> ids);
}
