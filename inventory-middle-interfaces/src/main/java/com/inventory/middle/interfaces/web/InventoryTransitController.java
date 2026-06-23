package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.InventoryTransitApplicationService;
import com.inventory.middle.application.service.InventoryTransitQueryService;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.command.InventoryTransitCommand;
import com.inventory.middle.client.dto.query.InventoryTransitPageQuery;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.List;
import top.kdla.framework.log.catchlog.CatchAndLog;


/**
 * 在途库存 Controller（从 inventory-center-bff InTransitStockController 迁移）
 * BFF 路径 /inTransitStock → 统一至 /inTransitStock（与 BFF 保持一致）
 *
 * 注意：BFF 原实现在分页结果中额外调用 remoteProductCenterRemoteService.getUnitById()
 * 填充单位名称。inventory-middle 本地 DB 中 uom 字段存储单位编码，
 * 单位名称填充待接入 ProductExternalService。
 */
@Tag(name = "在途库存管理")
@CatchAndLog
@RestController
@RequestMapping("/inTransitStock")
@Slf4j
public class InventoryTransitController {

    @Resource
    private InventoryTransitQueryService inventoryTransitQueryService;
    @Resource
    private InventoryTransitApplicationService inventoryTransitApplicationService;
    @Resource
    private RemoteProductCenterRestService remoteProductCenterRestService;

    @Operation(summary = "分页查询在途库存信息")
    @PostMapping("/page/query")
    public PageResponse<InventoryTransitDto> queryInTransitStockPage(@RequestBody InventoryTransitPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        PageResponse<InventoryTransitDto> result = inventoryTransitQueryService.queryPage(pageQuery);
        String tenantId = UserContextHolder.getTenantId();
        if (result != null && !CollectionUtils.isEmpty(result.getData())) {
            result.getData().forEach(dto -> {
                if (dto.getUom() != null) {
                    dto.setUomName(remoteProductCenterRestService.getUnitNameByCode(dto.getUom(), tenantId));
                }
            });
        }
        return result;
    }

    @Operation(summary = "在途库存分页查询（内部路径）")
    @PostMapping("/page")
    public PageResponse<InventoryTransitDto> page(@RequestBody InventoryTransitPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventoryTransitQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "在途库存详情")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryTransitDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventoryTransitQueryService.findById(id));
    }

    @Operation(summary = "保存在途库存")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@RequestBody InventoryTransitCommand command) {
        command.setTenantId(UserContextHolder.getTenantId());
        command.setCreatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(inventoryTransitApplicationService.add(command));
    }

    @Operation(summary = "更新在途库存")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@RequestBody InventoryTransitCommand command) {
        command.setUpdatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(inventoryTransitApplicationService.update(command));
    }

    @Operation(summary = "删除在途库存")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventoryTransitApplicationService.deleteBatch(ids));
    }
}