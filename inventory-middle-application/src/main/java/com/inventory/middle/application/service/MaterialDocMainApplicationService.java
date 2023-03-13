package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MaterialDocMainCommand;

import java.util.List;


/**
 * 物料凭证主表ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocMainApplicationService {

    /**
     * 保存
     *
     * @param materialdocmainCommand
     */
    boolean add(MaterialDocMainCommand materialdocmainCommand);

    /**
     * 更新
     *
     * @param materialdocmainCommand
     */
    boolean update(MaterialDocMainCommand materialdocmainCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
