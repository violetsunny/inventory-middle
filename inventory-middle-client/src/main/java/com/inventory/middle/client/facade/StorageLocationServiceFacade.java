package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.command.StorageLocationCommand;
import com.inventory.middle.client.dto.query.StorageLocationPageQuery;

import java.util.List;

/**
 * 存储地点表Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface StorageLocationServiceFacade {

    /**
     * 分页查询
     *
     * @param storagelocationPageQuery
     * @return
     */
    PageResponse<StorageLocationDto> page(StorageLocationPageQuery storagelocationPageQuery);

    /**
     * 存储地点表list查询
     */
    MultiResponse<StorageLocationDto> list();

    /**
     * 通过ID获取存储地点表
     *
     * @param id
     * @return
     */
    SingleResponse<StorageLocationDto> findById(Long id);

    /**
     * 保存
     *
     * @param storagelocationCommand
     */
    SingleResponse<Boolean> save(StorageLocationCommand storagelocationCommand);

    /**
     * 更新
     *
     * @param storagelocationCommand
     */
    SingleResponse<Boolean> update( StorageLocationCommand storagelocationCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
