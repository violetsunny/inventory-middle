package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryMonitorRuleLineQueryService;
import com.inventory.middle.application.service.InventoryMonitorRuleLineApplicationService;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;
import com.inventory.middle.application.convertor.InventoryMonitorRuleLineDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存预警规则明细Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 库存预警规则明细管理")
@RestController
@RequestMapping("/inventorymonitorruleline")
@Slf4j
@CatchAndLog
public class InventoryMonitorRuleLineController {

    @Resource
    private InventoryMonitorRuleLineApplicationService inventorymonitorrulelineApplicationService;
    @Resource
    private  InventoryMonitorRuleLineQueryService inventorymonitorrulelineQueryService;
    @Resource
    private InventoryMonitorRuleLineDtoConvertor  inventorymonitorrulelineDtoConvertor;


    /**
     * 库存预警规则明细分页查询
     */
    @Operation(summary="库存预警规则明细分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryMonitorRuleLineDto> page(@RequestBody InventoryMonitorRuleLinePageQuery inventorymonitorrulelinePageQuery) {
        return inventorymonitorrulelineQueryService.queryPage(inventorymonitorrulelinePageQuery);
    }

    /**
     * 库存预警规则明细list查询
     */
    @Operation(summary="库存预警规则明细list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryMonitorRuleLineDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存预警规则明细信息
     */
    @Operation(summary="库存预警规则明细信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryMonitorRuleLineDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorymonitorrulelineQueryService.findById(id));
    }

    /**
     * 保存库存预警规则明细
     */
    @Operation(summary="保存库存预警规则明细")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
        return SingleResponse.buildSuccess(inventorymonitorrulelineApplicationService.add(inventorymonitorrulelineCommand));

    }

    /**
     * 修改库存预警规则明细
     */
    @Operation(summary="修改库存预警规则明细")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
        return SingleResponse.buildSuccess(inventorymonitorrulelineApplicationService.update(inventorymonitorrulelineCommand));
    }

    /**
     * 删除库存预警规则明细
     */
    @Operation(summary="删除库存预警规则明细")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorymonitorrulelineApplicationService.deleteBatch(ids));
    }

}