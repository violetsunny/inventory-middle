package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;

/**
 * 库存-需求QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryDemandQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryDemandDto> queryPage(InventoryDemandPageQuery pageQuery);


    /**
     * 通过ID获取库存-需求
     *
     * @param id
     * @return
     */
    InventoryDemandDto findById(Long id);

}
