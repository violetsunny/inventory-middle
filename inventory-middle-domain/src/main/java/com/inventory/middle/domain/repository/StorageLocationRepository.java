package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 存储地点表Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface StorageLocationRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<StorageLocation> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取存储地点表
     *
     * @param id
     * @return
     */
     StorageLocation findById(StorageLocationId id);

    /**
     * 保存
     *
     * @param storagelocation
     */
    boolean store(StorageLocation storagelocation);

    /**
     * 更新
     *
     * @param storagelocation
     */
    boolean update(StorageLocation storagelocation);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<StorageLocationId> ids);
}
