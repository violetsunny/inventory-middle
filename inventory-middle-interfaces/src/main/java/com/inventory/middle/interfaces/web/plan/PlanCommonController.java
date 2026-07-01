package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.application.plan.common.service.PlanCommonApplicationService;
import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.application.service.WarehouseQueryService;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;
import com.inventory.middle.client.dto.query.WarehousePageQuery;
import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;
import com.inventory.middle.interfaces.support.UserContextHolder;
import com.inventory.middle.interfaces.web.plan.vo.MaterialInfoQueryResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Plan 公共资源 Controller（从 scm-plan-bff CommonController 迁移）
 * 路径与 BFF 保持一致：/common
 */
@Tag(name = "公共资源管理（plan）")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/common")
public class PlanCommonController {

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Resource
    private WarehouseQueryService warehouseQueryService;

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Resource
    private PlanCommonApplicationService planCommonApplicationService;

    @Operation(summary = "根据物料code查询物料信息")
    @PostMapping("/queryMaterialInfoByCode")
    public SingleResponse<MaterialInfoQueryResVO> queryMaterialInfoByCode(
            @RequestParam("materialCode") String materialCode) {
        String tenantId = UserContextHolder.getTenantId();
        List<com.inventory.middle.client.plan.dto.inventory.LogicalPlant> logicalPlants = Collections.emptyList();
        String materialName = materialCode;
        String unit = "";
        com.inventory.middle.domain.plan.common.bo.PlanProductBO product = planCommonApplicationService.queryMaterialByCode(materialCode, tenantId);
        if (product != null) {
            materialName = product.getName() != null ? product.getName() : materialCode;
            unit = product.getUnit() != null ? product.getUnit() : "";
        }
        MaterialInfoQueryResVO result = MaterialInfoQueryResVO.builder()
                .materialCode(materialCode)
                .materialName(materialName)
                .unit(unit)
                .logicalPlantList(logicalPlants)
                .build();
        return SingleResponse.buildSuccess(result);
    }

    @Operation(summary = "根据租户id查询逻辑仓")
    @PostMapping("/queryLogicalPlantByTenantId")
    public SingleResponse<List<LogicalPlantDto>> queryLogicalPlantByTenantId() {
        String tenantId = UserContextHolder.getTenantId();
        LogicalPlantPageQuery query = new LogicalPlantPageQuery();
        query.setTenantId(tenantId);
        List<LogicalPlantDto> logicalPlants = logicalPlantQueryService.list(query);
        return SingleResponse.buildSuccess(logicalPlants);
    }

    @Operation(summary = "根据租户id查询物理仓")
    @PostMapping("/queryWarehouseByTenantId")
    public SingleResponse<List<WarehouseDto>> queryWarehouseByTenantId() {
        String tenantId = UserContextHolder.getTenantId();
        WarehousePageQuery query = new WarehousePageQuery();
        query.setTenantId(tenantId);
        List<WarehouseDto> warehouses = warehouseQueryService.list(query);
        return SingleResponse.buildSuccess(warehouses);
    }

    @Operation(summary = "根据名称模糊查询用户")
    @PostMapping("/fuzzyQueryUserInfo")
    public SingleResponse<List<ParticipantUser>> fuzzyQueryUserInfo(
            @RequestParam("keywords") String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return SingleResponse.buildFailure("PARAM_ERROR", "关键字不能为空");
        }
        List<ParticipantUser> users = planCommonApplicationService.fuzzyQueryUserInfo(keywords);
        return SingleResponse.buildSuccess(users);
    }

    @Operation(summary = "模糊查询物料code")
    @PostMapping("/fuzzyQueryMaterialCode")
    public SingleResponse<List<String>> fuzzyQueryMaterialCode(
            @RequestParam("logicalPlantNo") String logicalPlantNo,
            @RequestParam("keywords") String keywords,
            @RequestParam(value = "maxResultSize", defaultValue = "20") int maxResultSize) {
        String tenantId = UserContextHolder.getTenantId();
        List<com.inventory.middle.client.material.dto.InventoryMaterialDTO> materials =
                inventoryMaterialApplicationService.fuzzyQueryByMaterialCodeAndLogicalPlant(
                        keywords, logicalPlantNo, tenantId, maxResultSize);
        List<String> codes = materials.stream()
                .map(com.inventory.middle.client.material.dto.InventoryMaterialDTO::getMaterialCode)
                .collect(java.util.stream.Collectors.toList());
        return SingleResponse.buildSuccess(codes);
    }

    @Operation(summary = "查询菜单权限")
    @PostMapping("/getMenuAndFunc")
    public SingleResponse<List<ParticipantMenuDTO>> getMenuAndFunc() {
        List<ParticipantMenuDTO> menus = planCommonApplicationService.getMenuAndFunc(
                UserContextHolder.getTenantId(), UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(menus);
    }
}
