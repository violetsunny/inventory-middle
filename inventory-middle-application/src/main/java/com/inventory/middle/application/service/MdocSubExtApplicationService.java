package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MdocSubExtCommand;

import java.util.List;


/**
 * 物料凭证-标签-扩展ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubExtApplicationService {

    /**
     * 保存
     *
     * @param mdocsubextCommand
     */
    boolean add(MdocSubExtCommand mdocsubextCommand);

    /**
     * 更新
     *
     * @param mdocsubextCommand
     */
    boolean update(MdocSubExtCommand mdocsubextCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
