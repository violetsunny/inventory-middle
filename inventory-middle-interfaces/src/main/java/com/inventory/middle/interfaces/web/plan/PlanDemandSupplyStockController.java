package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.application.plan.bom.service.BomCaseApplicationService;
import com.inventory.middle.application.plan.dss.PlanDemandSupplyStockApplicationService;
import com.inventory.middle.client.plan.bom.dto.BomTreeDTO;
import com.inventory.middle.client.plan.bom.dto.BomTreeRenderReqDTO;
import com.inventory.middle.client.plan.bom.dto.HierarchicalBomNodeDTO;
import com.inventory.middle.interfaces.support.UserContextHolder;
import com.inventory.middle.interfaces.web.plan.dto.PlanDemandSupplyStockBoardDetailReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanDemandSupplyStockBoardReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardBomVO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardDetailChartVO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardDetailDataVO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardDetailResVO;
import com.inventory.middle.interfaces.web.plan.vo.DemandSupplyStockBoardResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PagedSingleResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private BomCaseApplicationService bomCaseApplicationService;

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

    @Operation(summary = "物料供需存看板详情")
    @PostMapping("/demandSupplyStockBoardDetail")
    public SingleResponse<DemandSupplyStockBoardDetailResVO> demandSupplyStockBoardDetail(
            @RequestBody PlanDemandSupplyStockBoardDetailReqDTO requestDTO) {
        String tenantId = UserContextHolder.getTenantId();
        Map<String, Object> result = planDemandSupplyStockApplicationService.demandSupplyStockBoardDetail(
                requestDTO.getMaterialCode(), requestDTO.getLogicalPlantNo(), tenantId);

        DemandSupplyStockBoardDetailResVO resVO = new DemandSupplyStockBoardDetailResVO();

        Object boardDataObj = result.get("boardData");
        if (boardDataObj instanceof List) {
            List<Map<String, Object>> boardDataMaps = (List<Map<String, Object>>) boardDataObj;
            List<DemandSupplyStockBoardDetailDataVO> boardData = new ArrayList<>();
            for (Map<String, Object> row : boardDataMaps) {
                DemandSupplyStockBoardDetailDataVO vo = new DemandSupplyStockBoardDetailDataVO();
                if (row.get("planDate") instanceof Date) vo.setPlanDate((Date) row.get("planDate"));
                if (row.get("planElementType") instanceof Integer) vo.setPlanElementType((Integer) row.get("planElementType"));
                vo.setVoucherNo(row.get("voucherNo") != null ? row.get("voucherNo").toString() : null);
                vo.setDocumentNo(row.get("documentNo") != null ? row.get("documentNo").toString() : null);
                vo.setExceptionCode(row.get("exceptionCode") != null ? row.get("exceptionCode").toString() : null);
                vo.setDemandAmount(toLong(row.get("demandAmount")));
                vo.setSupplyAmount(toLong(row.get("supplyAmount")));
                vo.setAvailableAmount(toLong(row.get("availableAmount")));
                boardData.add(vo);
            }
            resVO.setBoardData(boardData);
        }

        Object chartDataObj = result.get("chartData");
        if (chartDataObj instanceof List) {
            List<Map<String, Object>> chartDataMaps = (List<Map<String, Object>>) chartDataObj;
            List<DemandSupplyStockBoardDetailChartVO> chartData = new ArrayList<>();
            for (Map<String, Object> row : chartDataMaps) {
                DemandSupplyStockBoardDetailChartVO vo = new DemandSupplyStockBoardDetailChartVO();
                vo.setDemandAmount(toLong(row.get("demandAmount")));
                vo.setSupplyAmount(toLong(row.get("supplyAmount")));
                chartData.add(vo);
            }
            resVO.setChartData(chartData);
        }

        return SingleResponse.buildSuccess(resVO);
    }

    private static Long toLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return null;
    }

    @Operation(summary = "渲染BOM树")
    @PostMapping("/renderBomTree")
    public SingleResponse<DemandSupplyStockBoardBomVO> renderBomTree(
            @RequestBody PlanDemandSupplyStockBoardDetailReqDTO requestDTO) {
        String tenantId = UserContextHolder.getTenantId();

        BomTreeRenderReqDTO bomReqDTO = new BomTreeRenderReqDTO();
        bomReqDTO.setMaterialCode(requestDTO.getMaterialCode());
        bomReqDTO.setLogicalPlantNo(requestDTO.getLogicalPlantNo());
        bomReqDTO.setTenantId(tenantId);
        bomReqDTO.setNodeAsRoot(true);
        bomReqDTO.setShowLeaf(true);
        bomReqDTO.setShowEnable(false);

        SingleResponse<ArrayList<BomTreeDTO>> bomResult = bomCaseApplicationService.renderBomTree(bomReqDTO);

        DemandSupplyStockBoardBomVO vo = new DemandSupplyStockBoardBomVO();
        vo.setMaterialCode(requestDTO.getMaterialCode());

        if (bomResult != null && bomResult.isSuccess()
                && CollectionUtils.isNotEmpty(bomResult.getData())) {
            // Take the first BOM tree root as the main node
            BomTreeDTO firstTree = bomResult.getData().get(0);
            if (firstTree != null && firstTree.getRoot() != null) {
                HierarchicalBomNodeDTO root = firstTree.getRoot();
                vo.setMaterialCode(root.getMaterialCode());
                vo.setMaterialDesc(root.getMaterialDesc());
                vo.setChildren(convertChildren(root.getChildren()));
            }
        } else {
            vo.setChildren(new ArrayList<>());
        }

        return SingleResponse.buildSuccess(vo);
    }

    /** Recursively convert HierarchicalBomNodeDTO tree to DemandSupplyStockBoardBomVO tree. */
    private ArrayList<DemandSupplyStockBoardBomVO> convertChildren(List<HierarchicalBomNodeDTO> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }
        ArrayList<DemandSupplyStockBoardBomVO> result = new ArrayList<>();
        for (HierarchicalBomNodeDTO node : nodes) {
            DemandSupplyStockBoardBomVO child = new DemandSupplyStockBoardBomVO();
            child.setMaterialCode(node.getMaterialCode());
            child.setMaterialDesc(node.getMaterialDesc());
            child.setChildren(convertChildren(node.getChildren()));
            result.add(child);
        }
        return result;
    }
}
