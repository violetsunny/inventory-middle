package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MdocSubQuantityCommand;

import java.util.List;


/**
 * 物料凭证子表-数量ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubQuantityApplicationService {

    /**
     * 保存
     *
     * @param mdocsubquantityCommand
     */
    boolean add(MdocSubQuantityCommand mdocsubquantityCommand);

    /**
     * 更新
     *
     * @param mdocsubquantityCommand
     */
    boolean update(MdocSubQuantityCommand mdocsubquantityCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
