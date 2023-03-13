package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventorySnapshotQueryService;
import com.inventory.middle.application.service.InventorySnapshotApplicationService;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.command.InventorySnapshotCommand;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;
import com.inventory.middle.application.convertor.InventorySnapshotDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存快照-实时Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 库存快照-实时管理")
@RestController
@RequestMapping("/inventorysnapshot")
@Slf4j
@CatchAndLog
public class InventorySnapshotController {

    @Resource
    private InventorySnapshotApplicationService inventorysnapshotApplicationService;
    @Resource
    private  InventorySnapshotQueryService inventorysnapshotQueryService;
    @Resource
    private InventorySnapshotDtoConvertor  inventorysnapshotDtoConvertor;


    /**
     * 库存快照-实时分页查询
     */
    @Operation(summary="库存快照-实时分页查询")
    @PostMapping("/page")
    public PageResponse<InventorySnapshotDto> page(@RequestBody InventorySnapshotPageQuery inventorysnapshotPageQuery) {
        return inventorysnapshotQueryService.queryPage(inventorysnapshotPageQuery);
    }

    /**
     * 库存快照-实时list查询
     */
    @Operation(summary="库存快照-实时list查询")
    @PostMapping("/list")
            public MultiResponse<InventorySnapshotDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存快照-实时信息
     */
    @Operation(summary="库存快照-实时信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventorySnapshotDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorysnapshotQueryService.findById(id));
    }

    /**
     * 保存库存快照-实时
     */
    @Operation(summary="保存库存快照-实时")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventorySnapshotCommand inventorysnapshotCommand) {
        return SingleResponse.buildSuccess(inventorysnapshotApplicationService.add(inventorysnapshotCommand));

    }

    /**
     * 修改库存快照-实时
     */
    @Operation(summary="修改库存快照-实时")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventorySnapshotCommand inventorysnapshotCommand) {
        return SingleResponse.buildSuccess(inventorysnapshotApplicationService.update(inventorysnapshotCommand));
    }

    /**
     * 删除库存快照-实时
     */
    @Operation(summary="删除库存快照-实时")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorysnapshotApplicationService.deleteBatch(ids));
    }

}