package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryAlertDto;
import com.inventory.middle.client.dto.query.InventoryAlertPageQuery;

/**
 * 库存报警日志QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
public interface InventoryAlertQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryAlertDto> queryPage(InventoryAlertPageQuery pageQuery);


    /**
     * 通过ID获取库存报警日志
     *
     * @param id
     * @return
     */
    InventoryAlertDto findById(Long id);

}
