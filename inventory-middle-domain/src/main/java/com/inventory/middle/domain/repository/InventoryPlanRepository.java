package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryPlan;
import com.inventory.middle.domain.model.types.InventoryPlanId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存-计划Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryPlanRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryPlan> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存-计划
     *
     * @param id
     * @return
     */
     InventoryPlan findById(InventoryPlanId id);

    /**
     * 保存
     *
     * @param inventoryplan
     */
    boolean store(InventoryPlan inventoryplan);

    /**
     * 更新
     *
     * @param inventoryplan
     */
    boolean update(InventoryPlan inventoryplan);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryPlanId> ids);
}
