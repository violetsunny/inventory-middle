package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MaterialDocItemCommand;

import java.util.List;


/**
 * 物料凭证-itemApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocItemApplicationService {

    /**
     * 保存
     *
     * @param materialdocitemCommand
     */
    boolean add(MaterialDocItemCommand materialdocitemCommand);

    /**
     * 更新
     *
     * @param materialdocitemCommand
     */
    boolean update(MaterialDocItemCommand materialdocitemCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
