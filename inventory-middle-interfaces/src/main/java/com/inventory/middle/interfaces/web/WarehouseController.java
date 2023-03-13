package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.WarehouseQueryService;
import com.inventory.middle.application.service.WarehouseApplicationService;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.command.WarehouseCommand;
import com.inventory.middle.client.dto.query.WarehousePageQuery;
import com.inventory.middle.application.convertor.WarehouseDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物理仓库表Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 物理仓库表管理")
@RestController
@RequestMapping("/warehouse")
@Slf4j
@CatchAndLog
public class WarehouseController {

    @Resource
    private WarehouseApplicationService warehouseApplicationService;
    @Resource
    private  WarehouseQueryService warehouseQueryService;
    @Resource
    private WarehouseDtoConvertor  warehouseDtoConvertor;


    /**
     * 物理仓库表分页查询
     */
    @Operation(summary="物理仓库表分页查询")
    @PostMapping("/page")
    public PageResponse<WarehouseDto> page(@RequestBody WarehousePageQuery warehousePageQuery) {
        return warehouseQueryService.queryPage(warehousePageQuery);
    }

    /**
     * 物理仓库表list查询
     */
    @Operation(summary="物理仓库表list查询")
    @PostMapping("/list")
            public MultiResponse<WarehouseDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物理仓库表信息
     */
    @Operation(summary="物理仓库表信息")
    @GetMapping("/find/{id}")
    public SingleResponse<WarehouseDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(warehouseQueryService.findById(id));
    }

    /**
     * 保存物理仓库表
     */
    @Operation(summary="保存物理仓库表")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody WarehouseCommand warehouseCommand) {
        return SingleResponse.buildSuccess(warehouseApplicationService.add(warehouseCommand));

    }

    /**
     * 修改物理仓库表
     */
    @Operation(summary="修改物理仓库表")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody WarehouseCommand warehouseCommand) {
        return SingleResponse.buildSuccess(warehouseApplicationService.update(warehouseCommand));
    }

    /**
     * 删除物理仓库表
     */
    @Operation(summary="删除物理仓库表")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(warehouseApplicationService.deleteBatch(ids));
    }

}