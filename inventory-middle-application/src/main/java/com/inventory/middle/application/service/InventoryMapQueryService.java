package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.query.InventoryMapPageQuery;

/**
 * 移动平均价QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryMapDto> queryPage(InventoryMapPageQuery pageQuery);


    /**
     * 通过ID获取移动平均价
     *
     * @param id
     * @return
     */
    InventoryMapDto findById(Long id);

}
