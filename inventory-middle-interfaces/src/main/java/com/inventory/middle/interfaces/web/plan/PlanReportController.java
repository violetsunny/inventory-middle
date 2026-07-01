package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.application.plan.calculate.service.PlanGenerateApplicationService;
import com.inventory.middle.interfaces.web.plan.PlanReportConverter;
import com.inventory.middle.interfaces.web.plan.dto.MaterialPlanQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.MaterialPlanBomTreeVO;
import com.inventory.middle.interfaces.web.plan.vo.MaterialPlanReportVO;
import com.inventory.middle.interfaces.web.plan.vo.MaterialPlanVO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceBomNodeDTO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceDTO;
import com.inventory.middle.client.plan.config.dto.MaterialPlanInstanceQueryRequest;
import com.inventory.middle.client.plan.config.dto.MaterialPlanReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.inventory.middle.interfaces.support.UserContextHolder;
import top.kdla.framework.dto.PagedSingleResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 物料计划报表服务
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Slf4j
@CatchAndLog
@RestController
@Tag(name = "物料计划报表")
@RequestMapping("/planReport")
public class PlanReportController  {

    @Resource
    private PlanReportConverter planReportConverter;

    @Resource
    private PlanGenerateApplicationService planGenerateApplicationService;

        @ResponseBody
    @PostMapping("/pageQueryMaterialPlans")
    @Operation(summary = "分页查询物料计划报表")
    public PagedSingleResponse<MaterialPlanVO> pageQueryMaterialPlans(@RequestBody MaterialPlanQueryReqDTO request) {

        MaterialPlanInstanceQueryRequest condition =
                planReportConverter.convertMaterialPlanQueryRequest(request);
        condition.setTenantId(UserContextHolder.getTenantId());

        PagedSingleResponse<MaterialPlanInstanceDTO> result =
                planGenerateApplicationService.pageQueryMaterialPlans(condition);

        return PagedSingleResponse.success(
                result.getPageNum(),
                result.getPageSize(),
                result.getTotalCount(),
                planReportConverter.convertMaterialPlans(result.getData()));
    }


        @ResponseBody
    @PostMapping("/queryMaterialInstanceReport")
    @Operation(summary = "查询物料计划报表详情")
    public SingleResponse<MaterialPlanReportVO> queryMaterialInstanceReport(
            @Parameter(description = "物料计划版本号")
            @RequestParam("materialInstanceId") Long materialInstanceId,
            @Parameter(description = "汇总类型:0-按日/1-按周/3-按月")
            @RequestParam(value = "collectType", required = false) Integer collectType) {

        MaterialPlanReportDTO report =
                planGenerateApplicationService.queryMaterialInstanceReport(materialInstanceId, UserContextHolder.getTenantId(), collectType);

        return SingleResponse.buildSuccess(planReportConverter.convertReport(report));
    }

        @ResponseBody
    @PostMapping("/renderMaterialBomTree")
    @Operation(summary = "物料报表页bom树渲染")
    public SingleResponse<MaterialPlanBomTreeVO> renderMaterialBomTree(
            @Parameter(description = "物料计划版本号")
            @RequestParam("materialInstanceId") Long materialInstanceId) {

        MaterialPlanInstanceBomNodeDTO materialPlanInstanceBomDTO =
                planGenerateApplicationService.renderMaterialBomTree(materialInstanceId, UserContextHolder.getTenantId()).getData();

        return SingleResponse.buildSuccess(planReportConverter.convertMaterialBomTree(materialPlanInstanceBomDTO));
    }

}
