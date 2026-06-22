package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryMapHisDto;
import com.inventory.middle.client.dto.query.InventoryMapHisPageQuery;

/**
 * 移动平均价历史记录QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapHisQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryMapHisDto> queryPage(InventoryMapHisPageQuery pageQuery);


    /**
     * 通过ID获取移动平均价历史记录
     *
     * @param id
     * @return
     */
    InventoryMapHisDto findById(Long id);

}
