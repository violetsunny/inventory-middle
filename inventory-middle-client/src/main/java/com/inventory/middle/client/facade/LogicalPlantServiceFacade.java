package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.command.LogicalPlantCommand;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;

import java.util.List;

/**
 * 逻辑仓库表Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface LogicalPlantServiceFacade {

    /**
     * 分页查询
     *
     * @param logicalplantPageQuery
     * @return
     */
    PageResponse<LogicalPlantDto> page(LogicalPlantPageQuery logicalplantPageQuery);

    /**
     * 逻辑仓库表list查询
     */
    MultiResponse<LogicalPlantDto> list();

    /**
     * 通过ID获取逻辑仓库表
     *
     * @param id
     * @return
     */
    SingleResponse<LogicalPlantDto> findById(Long id);

    /**
     * 保存
     *
     * @param logicalplantCommand
     */
    SingleResponse<Boolean> save(LogicalPlantCommand logicalplantCommand);

    /**
     * 更新
     *
     * @param logicalplantCommand
     */
    SingleResponse<Boolean> update( LogicalPlantCommand logicalplantCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
