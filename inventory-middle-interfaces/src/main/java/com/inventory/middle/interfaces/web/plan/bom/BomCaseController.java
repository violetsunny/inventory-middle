package com.inventory.middle.interfaces.web.plan.bom;

import com.inventory.middle.application.plan.bom.service.BomCaseApplicationService;
import com.inventory.middle.client.plan.bom.dto.*;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * BOM清单 Controller（从 scm-plan-bff 迁移）
 * 路径与 BFF 保持一致：/bomCase
 */
@Tag(name = "物料清单")
@CatchAndLog
@RestController
@RequestMapping("/bomCase")
@Slf4j
public class BomCaseController {

    @Resource
    private BomCaseApplicationService bomCaseApplicationService;

    @Operation(summary = "创建BOM清单")
    @PostMapping("/create")
    public SingleResponse<String> create(@RequestBody BomCaseConfigurationDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        dto.setUserId(UserContextHolder.getUserId());
        dto.setUserName(UserContextHolder.getUsername());
        return bomCaseApplicationService.create(dto);
    }

    @Operation(summary = "更新BOM清单")
    @PostMapping("/update")
    public SingleResponse<String> update(@RequestBody BomCaseConfigurationDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        dto.setUserId(UserContextHolder.getUserId());
        dto.setUserName(UserContextHolder.getUsername());
        return bomCaseApplicationService.update(dto);
    }

    @Operation(summary = "BOM清单列表")
    @PostMapping("/pageQueryBOM")
    public PageResponse<BomCaseQueryResDTO> pageQueryBom(@RequestBody BomCaseQueryReqDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        return bomCaseApplicationService.pageQueryBom(dto);
    }

    @Operation(summary = "BOM单及母件详情")
    @PostMapping("/detail/queryBasicDetail")
    public SingleResponse<BomCaseDetailDTO> queryBasicDetail(@RequestBody BomCaseDetailReqDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        return bomCaseApplicationService.queryBomCaseDetail(dto);
    }

    @Operation(summary = "分页查询子件详情")
    @PostMapping("/detail/pageQueryChildrenDetail")
    public PageResponse<BomNodeDTO> pageQueryChildrenDetail(@RequestBody BomCaseChildrenQueryReqDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        return bomCaseApplicationService.pageQueryChildrenDetail(dto);
    }

    @Operation(summary = "查询子件详情(不分页)")
    @PostMapping("/detail/queryChildrenDetail")
    public SingleResponse<ArrayList<BomNodeDTO>> queryChildrenDetail(@RequestBody BomCaseDetailReqDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        return bomCaseApplicationService.queryChildrenDetail(dto);
    }

    @Operation(summary = "渲染BOM树")
    @PostMapping("/renderBomTree")
    public SingleResponse<ArrayList<BomTreeDTO>> renderBomTree(@RequestBody BomTreeRenderReqDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        return bomCaseApplicationService.renderBomTree(dto);
    }

    @Operation(summary = "切换bom单生效状态")
    @PostMapping("/changeBomCaseStatus")
    public SingleResponse<Boolean> changeBomCaseStatus(@RequestBody BomChangeStatusReqDTO dto) {
        dto.setTenantId(UserContextHolder.getTenantId());
        return bomCaseApplicationService.bomChangeStatus(dto);
    }
}
