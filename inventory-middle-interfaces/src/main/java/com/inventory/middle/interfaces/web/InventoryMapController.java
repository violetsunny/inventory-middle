package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryMapQueryService;
import com.inventory.middle.application.service.InventoryMapApplicationService;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.command.InventoryMapCommand;
import com.inventory.middle.client.dto.query.InventoryMapPageQuery;
import com.inventory.middle.application.convertor.InventoryMapDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 移动平均价Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:28
 */
@Tag(name = " 移动平均价管理")
@RestController
@RequestMapping("/inventorymap")
@Slf4j
@CatchAndLog
public class InventoryMapController {

    @Resource
    private InventoryMapApplicationService inventorymapApplicationService;
    @Resource
    private  InventoryMapQueryService inventorymapQueryService;
    @Resource
    private InventoryMapDtoConvertor  inventorymapDtoConvertor;


    /**
     * 移动平均价分页查询
     */
    @Operation(summary="移动平均价分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryMapDto> page(@RequestBody InventoryMapPageQuery inventorymapPageQuery) {
        return inventorymapQueryService.queryPage(inventorymapPageQuery);
    }

    /**
     * 移动平均价list查询
     */
    @Operation(summary="移动平均价list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryMapDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 移动平均价信息
     */
    @Operation(summary="移动平均价信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryMapDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorymapQueryService.findById(id));
    }

    /**
     * 保存移动平均价
     */
    @Operation(summary="保存移动平均价")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryMapCommand inventorymapCommand) {
        return SingleResponse.buildSuccess(inventorymapApplicationService.add(inventorymapCommand));

    }

    /**
     * 修改移动平均价
     */
    @Operation(summary="修改移动平均价")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryMapCommand inventorymapCommand) {
        return SingleResponse.buildSuccess(inventorymapApplicationService.update(inventorymapCommand));
    }

    /**
     * 删除移动平均价
     */
    @Operation(summary="删除移动平均价")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorymapApplicationService.deleteBatch(ids));
    }

}