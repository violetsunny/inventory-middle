package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;

/**
 * 库存预警规则明细QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleLineQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryMonitorRuleLineDto> queryPage(InventoryMonitorRuleLinePageQuery pageQuery);


    /**
     * 通过ID获取库存预警规则明细
     *
     * @param id
     * @return
     */
    InventoryMonitorRuleLineDto findById(Long id);

}
