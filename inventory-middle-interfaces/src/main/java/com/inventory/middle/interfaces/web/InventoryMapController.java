package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.InventoryMapApplicationService;
import com.inventory.middle.application.service.InventoryMapQueryService;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.command.InventoryMapCommand;
import com.inventory.middle.client.dto.query.InventoryMapPageQuery;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.List;
import top.kdla.framework.log.catchlog.CatchAndLog;


/**
 * 移动平均价 Controller（从 inventory-center-bff MapController 迁移）
 * BFF 路径 /map → 统一至 /map（与 BFF 保持一致）
 */
@Tag(name = "移动平均价管理")
@CatchAndLog
@RestController
@RequestMapping("/map")
@Slf4j
public class InventoryMapController {

    @Resource
    private InventoryMapQueryService inventoryMapQueryService;
    @Resource
    private InventoryMapApplicationService inventoryMapApplicationService;

    @Operation(summary = "查询移动平均价（BFF /map/queryMap）")
    @PostMapping("/queryMap")
    public PageResponse<InventoryMapDto> queryMap(@RequestBody InventoryMapPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventoryMapQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "移动平均价分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryMapDto> page(@RequestBody InventoryMapPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventoryMapQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "移动平均价详情")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryMapDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventoryMapQueryService.findById(id));
    }

    @Operation(summary = "保存移动平均价")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@RequestBody InventoryMapCommand command) {
        command.setTenantId(UserContextHolder.getTenantId());
        command.setCreatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(inventoryMapApplicationService.add(command));
    }

    @Operation(summary = "更新移动平均价")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@RequestBody InventoryMapCommand command) {
        command.setUpdatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(inventoryMapApplicationService.update(command));
    }

    @Operation(summary = "删除移动平均价")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventoryMapApplicationService.deleteBatch(ids));
    }
}