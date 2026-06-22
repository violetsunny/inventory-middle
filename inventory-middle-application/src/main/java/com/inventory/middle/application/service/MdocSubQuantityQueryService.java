package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MdocSubQuantityDto;
import com.inventory.middle.client.dto.query.MdocSubQuantityPageQuery;

/**
 * 物料凭证子表-数量QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubQuantityQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MdocSubQuantityDto> queryPage(MdocSubQuantityPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证子表-数量
     *
     * @param id
     * @return
     */
    MdocSubQuantityDto findById(Long id);

}
