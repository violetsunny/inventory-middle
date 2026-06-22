package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;

import java.util.List;

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

    /**
     * 通过原始单号查询物料凭证
     *
     * @param originalNo 原始单据号
     * @return 物料凭证
     */
    MaterialDocMainDto getByOriginalNo(String originalNo);

    /**
     * 导出物料凭证列表（不分页）
     *
     * @param pageQuery 查询条件
     * @return 导出数据列表
     */
    List<MaterialDocMainDto> exportList(MaterialDocMainPageQuery pageQuery);

}

