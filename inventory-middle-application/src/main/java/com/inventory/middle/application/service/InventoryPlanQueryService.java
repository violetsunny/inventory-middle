package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryPlanDto;
import com.inventory.middle.client.dto.query.InventoryPlanPageQuery;

/**
 * 库存-计划QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryPlanQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryPlanDto> queryPage(InventoryPlanPageQuery pageQuery);


    /**
     * 通过ID获取库存-计划
     *
     * @param id
     * @return
     */
    InventoryPlanDto findById(Long id);

}
