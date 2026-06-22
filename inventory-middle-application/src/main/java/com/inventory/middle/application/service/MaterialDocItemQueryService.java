package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MaterialDocItemDto;
import com.inventory.middle.client.dto.query.MaterialDocItemPageQuery;

/**
 * 物料凭证-itemQueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocItemQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MaterialDocItemDto> queryPage(MaterialDocItemPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证-item
     *
     * @param id
     * @return
     */
    MaterialDocItemDto findById(Long id);

}
