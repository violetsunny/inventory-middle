package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MdocSubMapCommand;

import java.util.List;


/**
 * 物料凭证-标签-移动平均价ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMapApplicationService {

    /**
     * 保存
     *
     * @param mdocsubmapCommand
     */
    boolean add(MdocSubMapCommand mdocsubmapCommand);

    /**
     * 更新
     *
     * @param mdocsubmapCommand
     */
    boolean update(MdocSubMapCommand mdocsubmapCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
