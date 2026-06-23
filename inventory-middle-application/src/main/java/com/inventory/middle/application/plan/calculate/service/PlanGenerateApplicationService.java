package com.inventory.middle.application.plan.calculate.service;

import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceBomNodeDTO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceDTO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceQueryRequest;
import com.inventory.middle.client.plan.config.dto.MaterialPlanReportDTO;
import com.inventory.middle.client.plan.config.dto.PlanGenerateRequest;
import com.inventory.middle.client.plan.config.dto.PlanInstanceDTO;
import top.kdla.framework.dto.PagedSingleResponse;
import top.kdla.framework.dto.SingleResponse;

public interface PlanGenerateApplicationService {

    SingleResponse<PlanInstanceDTO> generate(PlanGenerateRequest request);

    PagedSingleResponse<MaterialPlanInstanceDTO> pageQueryMaterialPlans(MaterialPlanInstanceQueryRequest condition);

    MaterialPlanReportDTO queryMaterialInstanceReport(Long materialInstanceId, String tenantId, Integer collectType);

    SingleResponse<MaterialPlanInstanceBomNodeDTO> renderMaterialBomTree(Long materialInstanceId, String tenantId);
}
