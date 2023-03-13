package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MdocSubMaterialDto;
import com.inventory.middle.client.dto.query.MdocSubMaterialPageQuery;

/**
 * 物料凭证子表-物料信息QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMaterialQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<MdocSubMaterialDto> queryPage(MdocSubMaterialPageQuery pageQuery);


    /**
     * 通过ID获取物料凭证子表-物料信息
     *
     * @param id
     * @return
     */
    MdocSubMaterialDto findById(Long id);

}
