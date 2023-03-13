package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.query.WarehousePageQuery;

/**
 * 物理仓库表QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface WarehouseQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<WarehouseDto> queryPage(WarehousePageQuery pageQuery);


    /**
     * 通过ID获取物理仓库表
     *
     * @param id
     * @return
     */
    WarehouseDto findById(Long id);

}
