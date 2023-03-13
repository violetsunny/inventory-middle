package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;

/**
 * 库存-供给QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventorySupplyQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventorySupplyDto> queryPage(InventorySupplyPageQuery pageQuery);


    /**
     * 通过ID获取库存-供给
     *
     * @param id
     * @return
     */
    InventorySupplyDto findById(Long id);

}
