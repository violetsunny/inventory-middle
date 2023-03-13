package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryAlertQueryService;
import com.inventory.middle.application.service.InventoryAlertApplicationService;
import com.inventory.middle.client.dto.InventoryAlertDto;
import com.inventory.middle.client.dto.command.InventoryAlertCommand;
import com.inventory.middle.client.dto.query.InventoryAlertPageQuery;
import com.inventory.middle.application.convertor.InventoryAlertDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存报警日志Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 库存报警日志管理")
@RestController
@RequestMapping("/inventoryalert")
@Slf4j
@CatchAndLog
public class InventoryAlertController {

    @Resource
    private InventoryAlertApplicationService inventoryalertApplicationService;
    @Resource
    private  InventoryAlertQueryService inventoryalertQueryService;
    @Resource
    private InventoryAlertDtoConvertor  inventoryalertDtoConvertor;


    /**
     * 库存报警日志分页查询
     */
    @Operation(summary="库存报警日志分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryAlertDto> page(@RequestBody InventoryAlertPageQuery inventoryalertPageQuery) {
        return inventoryalertQueryService.queryPage(inventoryalertPageQuery);
    }

    /**
     * 库存报警日志list查询
     */
    @Operation(summary="库存报警日志list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryAlertDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存报警日志信息
     */
    @Operation(summary="库存报警日志信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryAlertDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventoryalertQueryService.findById(id));
    }

    /**
     * 保存库存报警日志
     */
    @Operation(summary="保存库存报警日志")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryAlertCommand inventoryalertCommand) {
        return SingleResponse.buildSuccess(inventoryalertApplicationService.add(inventoryalertCommand));

    }

    /**
     * 修改库存报警日志
     */
    @Operation(summary="修改库存报警日志")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryAlertCommand inventoryalertCommand) {
        return SingleResponse.buildSuccess(inventoryalertApplicationService.update(inventoryalertCommand));
    }

    /**
     * 删除库存报警日志
     */
    @Operation(summary="删除库存报警日志")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventoryalertApplicationService.deleteBatch(ids));
    }

}