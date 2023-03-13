package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MdocSubExtDto;
import com.inventory.middle.client.dto.query.MdocSubExtPageQuery;

/**
 * 物料凭证-标签-扩展QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubExtQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MdocSubExtDto> queryPage(MdocSubExtPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证-标签-扩展
     *
     * @param id
     * @return
     */
    MdocSubExtDto findById(Long id);

}
