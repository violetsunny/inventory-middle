package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MdocSubMaterialCommand;

import java.util.List;


/**
 * 物料凭证子表-物料信息ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMaterialApplicationService {

    /**
     * 保存
     *
     * @param mdocsubmaterialCommand
     */
    boolean add(MdocSubMaterialCommand mdocsubmaterialCommand);

    /**
     * 更新
     *
     * @param mdocsubmaterialCommand
     */
    boolean update(MdocSubMaterialCommand mdocsubmaterialCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
