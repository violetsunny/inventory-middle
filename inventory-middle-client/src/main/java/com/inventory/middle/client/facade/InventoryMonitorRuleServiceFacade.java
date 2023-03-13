package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRulePageQuery;

import java.util.List;

/**
 * 库存预警规则Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorymonitorrulePageQuery
     * @return
     */
    PageResponse<InventoryMonitorRuleDto> page(InventoryMonitorRulePageQuery inventorymonitorrulePageQuery);

    /**
     * 库存预警规则list查询
     */
    MultiResponse<InventoryMonitorRuleDto> list();

    /**
     * 通过ID获取库存预警规则
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryMonitorRuleDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorymonitorruleCommand
     */
    SingleResponse<Boolean> save(InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * 更新
     *
     * @param inventorymonitorruleCommand
     */
    SingleResponse<Boolean> update( InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
