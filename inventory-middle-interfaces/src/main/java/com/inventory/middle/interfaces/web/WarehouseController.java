package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.WarehouseApplicationService;
import com.inventory.middle.application.service.WarehouseQueryService;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.command.WarehouseCommand;
import com.inventory.middle.client.dto.query.WarehousePageQuery;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import top.kdla.framework.log.catchlog.CatchAndLog;


/**
 * 物理仓库 Controller（从 inventory-center-bff 迁移）
 * 路径与 BFF 保持一致：/warehouse
 */
@Tag(name = "物理仓库管理")
@CatchAndLog
@RestController
@RequestMapping("/warehouse")
@Slf4j
public class WarehouseController {

    @Resource
    private WarehouseApplicationService warehouseApplicationService;
    @Resource
    private WarehouseQueryService warehouseQueryService;

    @Operation(summary = "仓库分页查询")
    @PostMapping("/page")
    public PageResponse<WarehouseDto> page(@RequestBody WarehousePageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return warehouseQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "仓库列表查询")
    @PostMapping("/list")
    public MultiResponse<WarehouseDto> list(@RequestBody WarehousePageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return MultiResponse.buildSuccess(warehouseQueryService.list(pageQuery));
    }

    @Operation(summary = "创建仓库")
    @PostMapping("/create")
    public SingleResponse<Boolean> create(@RequestBody WarehouseCommand command) {
        command.setTenantId(UserContextHolder.getTenantId());
        command.setCreatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(warehouseApplicationService.add(command));
    }

    @Operation(summary = "更新仓库")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@RequestBody WarehouseCommand command) {
        command.setUpdatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(warehouseApplicationService.update(command));
    }

    @Operation(summary = "仓库详情")
    @PostMapping("/detail")
    public SingleResponse<WarehouseDto> detail(@RequestBody WarehousePageQuery query) {
        if (query.getId() != null) {
            return SingleResponse.buildSuccess(warehouseQueryService.findById(query.getId()));
        }
        log.warn("warehouse/detail: 按编号查询暂不支持（WarehouseRepository 无 findByWarehouseNo）");
        return SingleResponse.buildFailure("NOT_IMPLEMENTED", "按仓库编号查询详情暂未支持，请使用 id 查询");
    }
}