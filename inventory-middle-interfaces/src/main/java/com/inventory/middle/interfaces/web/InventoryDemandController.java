package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryDemandQueryService;
import com.inventory.middle.application.service.InventoryDemandApplicationService;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.command.InventoryDemandCommand;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;
import com.inventory.middle.application.convertor.InventoryDemandDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存-需求Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 库存-需求管理")
@RestController
@RequestMapping("/inventorydemand")
@Slf4j
@CatchAndLog
public class InventoryDemandController {

    @Resource
    private InventoryDemandApplicationService inventorydemandApplicationService;
    @Resource
    private  InventoryDemandQueryService inventorydemandQueryService;
    @Resource
    private InventoryDemandDtoConvertor  inventorydemandDtoConvertor;


    /**
     * 库存-需求分页查询
     */
    @Operation(summary="库存-需求分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryDemandDto> page(@RequestBody InventoryDemandPageQuery inventorydemandPageQuery) {
        return inventorydemandQueryService.queryPage(inventorydemandPageQuery);
    }

    /**
     * 库存-需求list查询
     */
    @Operation(summary="库存-需求list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryDemandDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存-需求信息
     */
    @Operation(summary="库存-需求信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryDemandDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorydemandQueryService.findById(id));
    }

    /**
     * 保存库存-需求
     */
    @Operation(summary="保存库存-需求")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryDemandCommand inventorydemandCommand) {
        return SingleResponse.buildSuccess(inventorydemandApplicationService.add(inventorydemandCommand));

    }

    /**
     * 修改库存-需求
     */
    @Operation(summary="修改库存-需求")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryDemandCommand inventorydemandCommand) {
        return SingleResponse.buildSuccess(inventorydemandApplicationService.update(inventorydemandCommand));
    }

    /**
     * 删除库存-需求
     */
    @Operation(summary="删除库存-需求")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorydemandApplicationService.deleteBatch(ids));
    }

}