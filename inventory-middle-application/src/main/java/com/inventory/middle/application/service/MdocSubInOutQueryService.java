package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MdocSubInOutDto;
import com.inventory.middle.client.dto.query.MdocSubInOutPageQuery;

/**
 * 物料凭证子表-出入库信息QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubInOutQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MdocSubInOutDto> queryPage(MdocSubInOutPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证子表-出入库信息
     *
     * @param id
     * @return
     */
    MdocSubInOutDto findById(Long id);

}
