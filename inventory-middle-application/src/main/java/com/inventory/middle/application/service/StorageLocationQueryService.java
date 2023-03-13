package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.query.StorageLocationPageQuery;

/**
 * 存储地点表QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface StorageLocationQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<StorageLocationDto> queryPage(StorageLocationPageQuery pageQuery);


    /**
     * 通过ID获取存储地点表
     *
     * @param id
     * @return
     */
    StorageLocationDto findById(Long id);

}
