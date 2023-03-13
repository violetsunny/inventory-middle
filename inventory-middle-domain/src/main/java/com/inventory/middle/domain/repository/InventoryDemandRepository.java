package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.model.types.InventoryDemandId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存-需求Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryDemandRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryDemand> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存-需求
     *
     * @param id
     * @return
     */
     InventoryDemand findById(InventoryDemandId id);

    /**
     * 保存
     *
     * @param inventorydemand
     */
    boolean store(InventoryDemand inventorydemand);

    /**
     * 更新
     *
     * @param inventorydemand
     */
    boolean update(InventoryDemand inventorydemand);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryDemandId> ids);
}
