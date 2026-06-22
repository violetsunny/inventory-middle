package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MdocSubMapDto;
import com.inventory.middle.client.dto.query.MdocSubMapPageQuery;

/**
 * 物料凭证-标签-移动平均价QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMapQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MdocSubMapDto> queryPage(MdocSubMapPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证-标签-移动平均价
     *
     * @param id
     * @return
     */
    MdocSubMapDto findById(Long id);

}
