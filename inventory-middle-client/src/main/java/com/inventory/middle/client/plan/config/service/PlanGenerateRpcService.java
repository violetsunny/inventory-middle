package com.inventory.middle.client.plan.config.service;

import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceBomNodeDTO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceDTO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceQueryRequest;
import com.inventory.middle.client.plan.config.dto.MaterialPlanReportDTO;
import com.inventory.middle.client.plan.config.dto.PlanGenerateRequest;
import com.inventory.middle.client.plan.config.dto.PlanInstanceDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 计划产出服务 RPC 接口
 */
public interface PlanGenerateRpcService {

    /** 计划执行 */
    SingleResponse<PlanInstanceDTO> generate(PlanGenerateRequest planGenerateRequest);

    /** 分页查询物料计划执行实例 */
    PageResponse<MaterialPlanInstanceDTO> pageQueryMaterialPlanInstances(MaterialPlanInstanceQueryRequest request);

    /** 查询物料计划执行报表 */
    SingleResponse<MaterialPlanReportDTO> queryByMaterialPlanInstance(Long materialInstanceId, String tenantId, Integer collectType);

    /** 渲染计划报表bom树 */
    SingleResponse<MaterialPlanInstanceBomNodeDTO> renderMaterialBomTree(Long materialInstanceId, String tenantId);
}
