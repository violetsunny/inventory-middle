package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存快照-实时Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventorySnapshotRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventorySnapshot> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存快照-实时
     *
     * @param id
     * @return
     */
     InventorySnapshot findById(InventorySnapshotId id);

    /**
     * 保存
     *
     * @param inventorysnapshot
     */
    boolean store(InventorySnapshot inventorysnapshot);

    /**
     * 更新
     *
     * @param inventorysnapshot
     */
    boolean update(InventorySnapshot inventorysnapshot);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventorySnapshotId> ids);
}
