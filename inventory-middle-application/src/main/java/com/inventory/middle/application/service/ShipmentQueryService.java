package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.ShipmentDto;
import com.inventory.middle.client.dto.query.ShipmentPageQuery;

/**
 * 交运单QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface ShipmentQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<ShipmentDto> queryPage(ShipmentPageQuery pageQuery);


    /**
     * 通过ID获取交运单
     *
     * @param id
     * @return
     */
    ShipmentDto findById(Long id);

}
