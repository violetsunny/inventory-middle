package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryMapHisQueryService;
import com.inventory.middle.application.service.InventoryMapHisApplicationService;
import com.inventory.middle.client.dto.InventoryMapHisDto;
import com.inventory.middle.client.dto.command.InventoryMapHisCommand;
import com.inventory.middle.client.dto.query.InventoryMapHisPageQuery;
import com.inventory.middle.application.convertor.InventoryMapHisDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 移动平均价历史记录Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 移动平均价历史记录管理")
@RestController
@RequestMapping("/inventorymaphis")
@Slf4j
@CatchAndLog
public class InventoryMapHisController {

    @Resource
    private InventoryMapHisApplicationService inventorymaphisApplicationService;
    @Resource
    private  InventoryMapHisQueryService inventorymaphisQueryService;
    @Resource
    private InventoryMapHisDtoConvertor  inventorymaphisDtoConvertor;


    /**
     * 移动平均价历史记录分页查询
     */
    @Operation(summary="移动平均价历史记录分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryMapHisDto> page(@RequestBody InventoryMapHisPageQuery inventorymaphisPageQuery) {
        return inventorymaphisQueryService.queryPage(inventorymaphisPageQuery);
    }

    /**
     * 移动平均价历史记录list查询
     */
    @Operation(summary="移动平均价历史记录list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryMapHisDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 移动平均价历史记录信息
     */
    @Operation(summary="移动平均价历史记录信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryMapHisDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorymaphisQueryService.findById(id));
    }

    /**
     * 保存移动平均价历史记录
     */
    @Operation(summary="保存移动平均价历史记录")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryMapHisCommand inventorymaphisCommand) {
        return SingleResponse.buildSuccess(inventorymaphisApplicationService.add(inventorymaphisCommand));

    }

    /**
     * 修改移动平均价历史记录
     */
    @Operation(summary="修改移动平均价历史记录")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryMapHisCommand inventorymaphisCommand) {
        return SingleResponse.buildSuccess(inventorymaphisApplicationService.update(inventorymaphisCommand));
    }

    /**
     * 删除移动平均价历史记录
     */
    @Operation(summary="删除移动平均价历史记录")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorymaphisApplicationService.deleteBatch(ids));
    }

}