package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;

import java.util.List;

/**
 * 库存预警规则明细Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleLineServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorymonitorrulelinePageQuery
     * @return
     */
    PageResponse<InventoryMonitorRuleLineDto> page(InventoryMonitorRuleLinePageQuery inventorymonitorrulelinePageQuery);

    /**
     * 库存预警规则明细list查询
     */
    MultiResponse<InventoryMonitorRuleLineDto> list();

    /**
     * 通过ID获取库存预警规则明细
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryMonitorRuleLineDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorymonitorrulelineCommand
     */
    SingleResponse<Boolean> save(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * 更新
     *
     * @param inventorymonitorrulelineCommand
     */
    SingleResponse<Boolean> update( InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
