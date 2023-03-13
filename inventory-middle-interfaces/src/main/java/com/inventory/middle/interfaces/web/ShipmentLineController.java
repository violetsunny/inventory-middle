package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.ShipmentLineQueryService;
import com.inventory.middle.application.service.ShipmentLineApplicationService;
import com.inventory.middle.client.dto.ShipmentLineDto;
import com.inventory.middle.client.dto.command.ShipmentLineCommand;
import com.inventory.middle.client.dto.query.ShipmentLinePageQuery;
import com.inventory.middle.application.convertor.ShipmentLineDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 交运单明细Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:28
 */
@Tag(name = " 交运单明细管理")
@RestController
@RequestMapping("/shipmentline")
@Slf4j
@CatchAndLog
public class ShipmentLineController {

    @Resource
    private ShipmentLineApplicationService shipmentlineApplicationService;
    @Resource
    private  ShipmentLineQueryService shipmentlineQueryService;
    @Resource
    private ShipmentLineDtoConvertor  shipmentlineDtoConvertor;


    /**
     * 交运单明细分页查询
     */
    @Operation(summary="交运单明细分页查询")
    @PostMapping("/page")
    public PageResponse<ShipmentLineDto> page(@RequestBody ShipmentLinePageQuery shipmentlinePageQuery) {
        return shipmentlineQueryService.queryPage(shipmentlinePageQuery);
    }

    /**
     * 交运单明细list查询
     */
    @Operation(summary="交运单明细list查询")
    @PostMapping("/list")
            public MultiResponse<ShipmentLineDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 交运单明细信息
     */
    @Operation(summary="交运单明细信息")
    @GetMapping("/find/{id}")
    public SingleResponse<ShipmentLineDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(shipmentlineQueryService.findById(id));
    }

    /**
     * 保存交运单明细
     */
    @Operation(summary="保存交运单明细")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody ShipmentLineCommand shipmentlineCommand) {
        return SingleResponse.buildSuccess(shipmentlineApplicationService.add(shipmentlineCommand));

    }

    /**
     * 修改交运单明细
     */
    @Operation(summary="修改交运单明细")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody ShipmentLineCommand shipmentlineCommand) {
        return SingleResponse.buildSuccess(shipmentlineApplicationService.update(shipmentlineCommand));
    }

    /**
     * 删除交运单明细
     */
    @Operation(summary="删除交运单明细")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(shipmentlineApplicationService.deleteBatch(ids));
    }

}