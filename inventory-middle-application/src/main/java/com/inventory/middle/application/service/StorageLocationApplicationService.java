package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.StorageLocationCommand;

import java.util.List;


/**
 * 存储地点表ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface StorageLocationApplicationService {

    /**
     * 保存
     *
     * @param storagelocationCommand
     */
    boolean add(StorageLocationCommand storagelocationCommand);

    /**
     * 更新
     *
     * @param storagelocationCommand
     */
    boolean update(StorageLocationCommand storagelocationCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
