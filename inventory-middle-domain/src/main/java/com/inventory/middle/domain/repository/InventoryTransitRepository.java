package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryTransit;
import com.inventory.middle.domain.model.types.InventoryTransitId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存-在途Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryTransitRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryTransit> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存-在途
     *
     * @param id
     * @return
     */
     InventoryTransit findById(InventoryTransitId id);

    /**
     * 保存
     *
     * @param inventorytransit
     */
    boolean store(InventoryTransit inventorytransit);

    /**
     * 更新
     *
     * @param inventorytransit
     */
    boolean update(InventoryTransit inventorytransit);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryTransitId> ids);
}
