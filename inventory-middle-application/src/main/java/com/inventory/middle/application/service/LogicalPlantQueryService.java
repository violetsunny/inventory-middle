package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;

import java.util.List;

/**
 * 逻辑仓库表QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface LogicalPlantQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<LogicalPlantDto> queryPage(LogicalPlantPageQuery pageQuery);


    /**
     * 通过ID获取逻辑仓库表
     *
     * @param id
     * @return
     */
    LogicalPlantDto findById(Long id);


    /** 不分页全量列表（by tenantId/warehouseNo 等条件） */
    java.util.List<LogicalPlantDto> list(LogicalPlantPageQuery pageQuery);

    List<InvPlantBO> list(String tenantId);

    /** 按编码查询逻辑仓详情 */
    LogicalPlantDto findByNo(String logicalPlantNo);

    /**
     * 按外部仓库编码查询逻辑仓（plan 迁移：queryLogicalPlantByOutPlantNo）
     * 注意：原 scm-plan-management 约定用 logicalPlantName 存储外部仓编码
     */
    LogicalPlantDto findByOutPlantNo(String outPlantNo, String tenantId);

}
