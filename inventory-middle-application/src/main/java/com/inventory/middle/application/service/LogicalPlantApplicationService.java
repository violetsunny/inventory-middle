package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.LogicalPlantCommand;

import java.util.List;


/**
 * 逻辑仓库表ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface LogicalPlantApplicationService {

    /**
     * 保存
     *
     * @param logicalplantCommand
     */
    boolean add(LogicalPlantCommand logicalplantCommand);

    /**
     * 更新
     *
     * @param logicalplantCommand
     */
    boolean update(LogicalPlantCommand logicalplantCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
