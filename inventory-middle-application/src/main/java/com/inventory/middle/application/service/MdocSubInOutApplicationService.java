package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MdocSubInOutCommand;

import java.util.List;


/**
 * 物料凭证子表-出入库信息ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubInOutApplicationService {

    /**
     * 保存
     *
     * @param mdocsubinoutCommand
     */
    boolean add(MdocSubInOutCommand mdocsubinoutCommand);

    /**
     * 更新
     *
     * @param mdocsubinoutCommand
     */
    boolean update(MdocSubInOutCommand mdocsubinoutCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
