package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存预警规则Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryMonitorRule> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存预警规则
     *
     * @param id
     * @return
     */
     InventoryMonitorRule findById(InventoryMonitorRuleId id);

    /**
     * 保存
     *
     * @param inventorymonitorrule
     */
    boolean store(InventoryMonitorRule inventorymonitorrule);

    /**
     * 更新
     *
     * @param inventorymonitorrule
     */
    boolean update(InventoryMonitorRule inventorymonitorrule);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryMonitorRuleId> ids);
}
