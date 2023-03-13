package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;

/**
 * 库存快照-实时QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventorySnapshotQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventorySnapshotDto> queryPage(InventorySnapshotPageQuery pageQuery);


    /**
     * 通过ID获取库存快照-实时
     *
     * @param id
     * @return
     */
    InventorySnapshotDto findById(Long id);

}
