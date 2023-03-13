package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryMonitorRuleQueryService;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRulePageQuery;
import com.inventory.middle.application.convertor.InventoryMonitorRuleDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存预警规则Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 库存预警规则管理")
@RestController
@RequestMapping("/inventorymonitorrule")
@Slf4j
@CatchAndLog
public class InventoryMonitorRuleController {

    @Resource
    private InventoryMonitorRuleApplicationService inventorymonitorruleApplicationService;
    @Resource
    private  InventoryMonitorRuleQueryService inventorymonitorruleQueryService;
    @Resource
    private InventoryMonitorRuleDtoConvertor  inventorymonitorruleDtoConvertor;


    /**
     * 库存预警规则分页查询
     */
    @Operation(summary="库存预警规则分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryMonitorRuleDto> page(@RequestBody InventoryMonitorRulePageQuery inventorymonitorrulePageQuery) {
        return inventorymonitorruleQueryService.queryPage(inventorymonitorrulePageQuery);
    }

    /**
     * 库存预警规则list查询
     */
    @Operation(summary="库存预警规则list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryMonitorRuleDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存预警规则信息
     */
    @Operation(summary="库存预警规则信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryMonitorRuleDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorymonitorruleQueryService.findById(id));
    }

    /**
     * 保存库存预警规则
     */
    @Operation(summary="保存库存预警规则")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryMonitorRuleCommand inventorymonitorruleCommand) {
        return SingleResponse.buildSuccess(inventorymonitorruleApplicationService.add(inventorymonitorruleCommand));

    }

    /**
     * 修改库存预警规则
     */
    @Operation(summary="修改库存预警规则")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryMonitorRuleCommand inventorymonitorruleCommand) {
        return SingleResponse.buildSuccess(inventorymonitorruleApplicationService.update(inventorymonitorruleCommand));
    }

    /**
     * 删除库存预警规则
     */
    @Operation(summary="删除库存预警规则")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorymonitorruleApplicationService.deleteBatch(ids));
    }

}