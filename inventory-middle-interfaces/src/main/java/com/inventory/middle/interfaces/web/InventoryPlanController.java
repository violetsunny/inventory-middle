package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryPlanQueryService;
import com.inventory.middle.application.service.InventoryPlanApplicationService;
import com.inventory.middle.client.dto.InventoryPlanDto;
import com.inventory.middle.client.dto.command.InventoryPlanCommand;
import com.inventory.middle.client.dto.query.InventoryPlanPageQuery;
import com.inventory.middle.application.convertor.InventoryPlanDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存-计划Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 库存-计划管理")
@RestController
@RequestMapping("/inventoryplan")
@Slf4j
@CatchAndLog
public class InventoryPlanController {

    @Resource
    private InventoryPlanApplicationService inventoryplanApplicationService;
    @Resource
    private  InventoryPlanQueryService inventoryplanQueryService;
    @Resource
    private InventoryPlanDtoConvertor  inventoryplanDtoConvertor;


    /**
     * 库存-计划分页查询
     */
    @Operation(summary="库存-计划分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryPlanDto> page(@RequestBody InventoryPlanPageQuery inventoryplanPageQuery) {
        return inventoryplanQueryService.queryPage(inventoryplanPageQuery);
    }

    /**
     * 库存-计划list查询
     */
    @Operation(summary="库存-计划list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryPlanDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存-计划信息
     */
    @Operation(summary="库存-计划信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryPlanDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventoryplanQueryService.findById(id));
    }

    /**
     * 保存库存-计划
     */
    @Operation(summary="保存库存-计划")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryPlanCommand inventoryplanCommand) {
        return SingleResponse.buildSuccess(inventoryplanApplicationService.add(inventoryplanCommand));

    }

    /**
     * 修改库存-计划
     */
    @Operation(summary="修改库存-计划")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryPlanCommand inventoryplanCommand) {
        return SingleResponse.buildSuccess(inventoryplanApplicationService.update(inventoryplanCommand));
    }

    /**
     * 删除库存-计划
     */
    @Operation(summary="删除库存-计划")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventoryplanApplicationService.deleteBatch(ids));
    }

}