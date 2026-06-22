package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MdocSubFinanceCommand;

import java.util.List;


/**
 * 物料凭证-标签-财务ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubFinanceApplicationService {

    /**
     * 保存
     *
     * @param mdocsubfinanceCommand
     */
    boolean add(MdocSubFinanceCommand mdocsubfinanceCommand);

    /**
     * 更新
     *
     * @param mdocsubfinanceCommand
     */
    boolean update(MdocSubFinanceCommand mdocsubfinanceCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
