package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.query.InventoryMonitorRulePageQuery;

/**
 * 库存预警规则QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryMonitorRuleDto> queryPage(InventoryMonitorRulePageQuery pageQuery);


    /**
     * 通过ID获取库存预警规则
     *
     * @param id
     * @return
     */
    InventoryMonitorRuleDto findById(Long id);

}
