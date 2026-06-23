package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.interfaces.support.UserContextHolder;
import com.inventory.middle.interfaces.web.plan.dto.PlanDemandSupplyStockBoardDetailReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanDemandSupplyStockBoardReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardBomVO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardDetailResVO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PagedSingleResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import java.util.Collections;

/**
 * 计划供需存 Controller（从 scm-plan-bff 迁移）
 * 路径与 BFF 保持一致：/planDemandSupplyStock
 *
 * TODO: 待接入 PlanDemandSupplyStockApplicationService（application 层尚无实现）
 */
@Tag(name = "计划供需存")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/planDemandSupplyStock")
public class PlanDemandSupplyStockController {

    @Operation(summary = "物料供需存看板")
    @PostMapping("/demandSupplyStockBoard")
    public PagedSingleResponse<DemandSupplyStockBoardResVO> demandSupplyStockBoard(
            @RequestBody PlanDemandSupplyStockBoardReqDTO requestDTO) {
        // TODO: 待接入 PlanDemandSupplyStockApplicationService.demandSupplyStockBoard()
        log.warn("demandSupplyStockBoard: 尚未接入，tenantId={}, logicalPlantNo={}",
                UserContextHolder.getTenantId(), requestDTO.getLogicalPlantNo());
        return PagedSingleResponse.success(
                requestDTO.getPageNum(), requestDTO.getPageSize(), 0L, Collections.emptyList());
    }

    @Operation(summary = "物料供需存看板详情")
    @PostMapping("/demandSupplyStockBoardDetail")
    public SingleResponse<DemandSupplyStockBoardDetailResVO> demandSupplyStockBoardDetail(
            @RequestBody PlanDemandSupplyStockBoardDetailReqDTO requestDTO) {
        // TODO: 待接入 PlanDemandSupplyStockApplicationService.demandSupplyStockBoardDetail()
        log.warn("demandSupplyStockBoardDetail: 尚未接入，tenantId={}, materialCode={}",
                UserContextHolder.getTenantId(), requestDTO.getMaterialCode());
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "渲染BOM树")
    @PostMapping("/renderBomTree")
    public SingleResponse<DemandSupplyStockBoardBomVO> renderBomTree(
            @RequestBody PlanDemandSupplyStockBoardDetailReqDTO requestDTO) {
        // TODO: 待接入 PlanDemandSupplyStockApplicationService.renderBomTree()
        log.warn("renderBomTree: 尚未接入，tenantId={}, materialCode={}",
                UserContextHolder.getTenantId(), requestDTO.getMaterialCode());
        return SingleResponse.buildSuccess(null);
    }
}
