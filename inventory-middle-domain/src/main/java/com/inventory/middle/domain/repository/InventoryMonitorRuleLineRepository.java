package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存预警规则明细Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleLineRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryMonitorRuleLine> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存预警规则明细
     *
     * @param id
     * @return
     */
     InventoryMonitorRuleLine findById(InventoryMonitorRuleLineId id);

    /**
     * 保存
     *
     * @param inventorymonitorruleline
     */
    boolean store(InventoryMonitorRuleLine inventorymonitorruleline);

    /**
     * 更新
     *
     * @param inventorymonitorruleline
     */
    boolean update(InventoryMonitorRuleLine inventorymonitorruleline);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryMonitorRuleLineId> ids);
}
