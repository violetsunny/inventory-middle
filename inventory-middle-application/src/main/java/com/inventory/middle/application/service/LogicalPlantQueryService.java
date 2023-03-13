package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;

/**
 * 逻辑仓库表QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface LogicalPlantQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<LogicalPlantDto> queryPage(LogicalPlantPageQuery pageQuery);


    /**
     * 通过ID获取逻辑仓库表
     *
     * @param id
     * @return
     */
    LogicalPlantDto findById(Long id);

}
