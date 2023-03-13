package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventorySupplyQueryService;
import com.inventory.middle.application.service.InventorySupplyApplicationService;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.command.InventorySupplyCommand;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;
import com.inventory.middle.application.convertor.InventorySupplyDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存-供给Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 库存-供给管理")
@RestController
@RequestMapping("/inventorysupply")
@Slf4j
@CatchAndLog
public class InventorySupplyController {

    @Resource
    private InventorySupplyApplicationService inventorysupplyApplicationService;
    @Resource
    private  InventorySupplyQueryService inventorysupplyQueryService;
    @Resource
    private InventorySupplyDtoConvertor  inventorysupplyDtoConvertor;


    /**
     * 库存-供给分页查询
     */
    @Operation(summary="库存-供给分页查询")
    @PostMapping("/page")
    public PageResponse<InventorySupplyDto> page(@RequestBody InventorySupplyPageQuery inventorysupplyPageQuery) {
        return inventorysupplyQueryService.queryPage(inventorysupplyPageQuery);
    }

    /**
     * 库存-供给list查询
     */
    @Operation(summary="库存-供给list查询")
    @PostMapping("/list")
            public MultiResponse<InventorySupplyDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存-供给信息
     */
    @Operation(summary="库存-供给信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventorySupplyDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorysupplyQueryService.findById(id));
    }

    /**
     * 保存库存-供给
     */
    @Operation(summary="保存库存-供给")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventorySupplyCommand inventorysupplyCommand) {
        return SingleResponse.buildSuccess(inventorysupplyApplicationService.add(inventorysupplyCommand));

    }

    /**
     * 修改库存-供给
     */
    @Operation(summary="修改库存-供给")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventorySupplyCommand inventorysupplyCommand) {
        return SingleResponse.buildSuccess(inventorysupplyApplicationService.update(inventorysupplyCommand));
    }

    /**
     * 删除库存-供给
     */
    @Operation(summary="删除库存-供给")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorysupplyApplicationService.deleteBatch(ids));
    }

}