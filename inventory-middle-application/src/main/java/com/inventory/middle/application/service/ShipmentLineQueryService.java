package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.ShipmentLineDto;
import com.inventory.middle.client.dto.query.ShipmentLinePageQuery;

/**
 * 交运单明细QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
public interface ShipmentLineQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<ShipmentLineDto> queryPage(ShipmentLinePageQuery pageQuery);


    /**
     * 通过ID获取交运单明细
     *
     * @param id
     * @return
     */
    ShipmentLineDto findById(Long id);

}
