package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.application.plan.dss.PlanDemandSupplyStockApplicationService;
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

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "计划供需存")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/planDemandSupplyStock")
public class PlanDemandSupplyStockController {

    @Resource
    private PlanDemandSupplyStockApplicationService planDemandSupplyStockApplicationService;

    @SuppressWarnings("unchecked")
    @Operation(summary = "物料供需存看板")
    @PostMapping("/demandSupplyStockBoard")
    public PagedSingleResponse<DemandSupplyStockBoardResVO> demandSupplyStockBoard(
            @RequestBody PlanDemandSupplyStockBoardReqDTO requestDTO) {
        String tenantId = UserContextHolder.getTenantId();
        Map<String, Object> result = planDemandSupplyStockApplicationService.demandSupplyStockBoard(
                requestDTO.getLogicalPlantNo(), requestDTO.getMaterialCode(),
                tenantId, requestDTO.getPageNum(), requestDTO.getPageSize());
        List<DemandSupplyStockBoardResVO> list = (List<DemandSupplyStockBoardResVO>) result.get("list");
        long total = (long) result.get("total");
        return PagedSingleResponse.success(requestDTO.getPageNum(), requestDTO.getPageSize(), total, list);
    }

    @SuppressWarnings("unchecked")
    @Operation(summary = "物料供需存看板详情")
    @PostMapping("/demandSupplyStockBoardDetail")
    public SingleResponse<DemandSupplyStockBoardDetailResVO> demandSupplyStockBoardDetail(
            @RequestBody PlanDemandSupplyStockBoardDetailReqDTO requestDTO) {
        String tenantId = UserContextHolder.getTenantId();
        Map<String, Object> result = planDemandSupplyStockApplicationService.demandSupplyStockBoardDetail(
                requestDTO.getMaterialCode(), requestDTO.getLogicalPlantNo(), tenantId);
        DemandSupplyStockBoardDetailResVO resVO = new DemandSupplyStockBoardDetailResVO();
        return SingleResponse.buildSuccess(resVO);
    }

    @SuppressWarnings("unchecked")
    @Operation(summary = "渲染BOM树")
    @PostMapping("/renderBomTree")
    public SingleResponse<DemandSupplyStockBoardBomVO> renderBomTree(
            @RequestBody PlanDemandSupplyStockBoardDetailReqDTO requestDTO) {
        String tenantId = UserContextHolder.getTenantId();
        Map<String, Object> result = planDemandSupplyStockApplicationService.renderBomTree(
                requestDTO.getMaterialCode(), requestDTO.getLogicalPlantNo(), tenantId);
        DemandSupplyStockBoardBomVO vo = new DemandSupplyStockBoardBomVO();
        vo.setMaterialCode((String) result.get("materialCode"));
        vo.setMaterialDesc((String) result.get("materialDesc"));
        vo.setChildren(new java.util.ArrayList<>());
        return SingleResponse.buildSuccess(vo);
    }
}
