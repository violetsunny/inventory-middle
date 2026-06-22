package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MdocSubFinanceDto;
import com.inventory.middle.client.dto.query.MdocSubFinancePageQuery;

/**
 * 物料凭证-标签-财务QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubFinanceQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MdocSubFinanceDto> queryPage(MdocSubFinancePageQuery pageQuery);


    /**
     * 通过ID获取物料凭证-标签-财务
     *
     * @param id
     * @return
     */
    MdocSubFinanceDto findById(Long id);

}
