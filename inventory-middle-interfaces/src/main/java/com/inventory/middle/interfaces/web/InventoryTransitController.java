package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.InventoryTransitQueryService;
import com.inventory.middle.application.service.InventoryTransitApplicationService;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.command.InventoryTransitCommand;
import com.inventory.middle.client.dto.query.InventoryTransitPageQuery;
import com.inventory.middle.application.convertor.InventoryTransitDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 库存-在途Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 库存-在途管理")
@RestController
@RequestMapping("/inventorytransit")
@Slf4j
@CatchAndLog
public class InventoryTransitController {

    @Resource
    private InventoryTransitApplicationService inventorytransitApplicationService;
    @Resource
    private  InventoryTransitQueryService inventorytransitQueryService;
    @Resource
    private InventoryTransitDtoConvertor  inventorytransitDtoConvertor;


    /**
     * 库存-在途分页查询
     */
    @Operation(summary="库存-在途分页查询")
    @PostMapping("/page")
    public PageResponse<InventoryTransitDto> page(@RequestBody InventoryTransitPageQuery inventorytransitPageQuery) {
        return inventorytransitQueryService.queryPage(inventorytransitPageQuery);
    }

    /**
     * 库存-在途list查询
     */
    @Operation(summary="库存-在途list查询")
    @PostMapping("/list")
            public MultiResponse<InventoryTransitDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 库存-在途信息
     */
    @Operation(summary="库存-在途信息")
    @GetMapping("/find/{id}")
    public SingleResponse<InventoryTransitDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(inventorytransitQueryService.findById(id));
    }

    /**
     * 保存库存-在途
     */
    @Operation(summary="保存库存-在途")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody InventoryTransitCommand inventorytransitCommand) {
        return SingleResponse.buildSuccess(inventorytransitApplicationService.add(inventorytransitCommand));

    }

    /**
     * 修改库存-在途
     */
    @Operation(summary="修改库存-在途")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody InventoryTransitCommand inventorytransitCommand) {
        return SingleResponse.buildSuccess(inventorytransitApplicationService.update(inventorytransitCommand));
    }

    /**
     * 删除库存-在途
     */
    @Operation(summary="删除库存-在途")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(inventorytransitApplicationService.deleteBatch(ids));
    }

}