package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryAlertNotificationQueryService;
import com.inventory.middle.application.service.InventoryAlertNotificationApplicationService;
import com.inventory.middle.client.dto.InventoryAlertNotificationDto;
import com.inventory.middle.client.dto.command.InventoryAlertNotificationCommand;
import com.inventory.middle.client.dto.query.InventoryAlertNotificationPageQuery;
import com.inventory.middle.application.convertor.InventoryAlertNotificationDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存报警通知记录Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 库存报警通知记录管理")
@RestController
@RequestMapping("/inventoryalertnotification")
@Slf4j
@CatchAndLog
public class InventoryAlertNotificationController {

    @Resource
    private InventoryAlertNotificationApplicationService inventoryalertnotificationApplicationService;
    @Resource
    private  InventoryAlertNotificationQueryService inventoryalertnotificationQueryService;
    @Resource
    private InventoryAlertNotificationDtoConvertor  inventoryalertnotificationDtoConvertor;


    /**
     * 库存报警通知记录分页查询
     */
    @Operation(summary="库存报警通知记录分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryAlertNotificationDto> page(@RequestBody InventoryAlertNotificationPageQuery inventoryalertnotificationPageQuery) {
        return inventoryalertnotificationQueryService.queryPage(inventoryalertnotificationPageQuery);
    }

    /**
     * 库存报警通知记录list查询
     */
    @Operation(summary="库存报警通知记录list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryAlertNotificationDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存报警通知记录信息
     */
    @Operation(summary="库存报警通知记录信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryAlertNotificationDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventoryalertnotificationQueryService.findById(id));
    }

    /**
     * 保存库存报警通知记录
     */
    @Operation(summary="保存库存报警通知记录")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryAlertNotificationCommand inventoryalertnotificationCommand) {
        return SingleResponse.buildSuccess(inventoryalertnotificationApplicationService.add(inventoryalertnotificationCommand));

    }

    /**
     * 修改库存报警通知记录
     */
    @Operation(summary="修改库存报警通知记录")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryAlertNotificationCommand inventoryalertnotificationCommand) {
        return SingleResponse.buildSuccess(inventoryalertnotificationApplicationService.update(inventoryalertnotificationCommand));
    }

    /**
     * 删除库存报警通知记录
     */
    @Operation(summary="删除库存报警通知记录")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventoryalertnotificationApplicationService.deleteBatch(ids));
    }

}