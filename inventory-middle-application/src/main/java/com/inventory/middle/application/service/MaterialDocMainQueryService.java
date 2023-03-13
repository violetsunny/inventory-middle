package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;

/**
 * 物料凭证主表QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocMainQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MaterialDocMainDto> queryPage(MaterialDocMainPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证主表
     *
     * @param id
     * @return
     */
    MaterialDocMainDto findById(Long id);

}
