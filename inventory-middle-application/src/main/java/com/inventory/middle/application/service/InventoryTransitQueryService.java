package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.query.InventoryTransitPageQuery;

/**
 * 库存-在途QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryTransitQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryTransitDto> queryPage(InventoryTransitPageQuery pageQuery);


    /**
     * 通过ID获取库存-在途
     *
     * @param id
     * @return
     */
    InventoryTransitDto findById(Long id);

}
