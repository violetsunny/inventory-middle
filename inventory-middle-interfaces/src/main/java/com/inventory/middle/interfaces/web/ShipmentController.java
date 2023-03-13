package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.ShipmentQueryService;
import com.inventory.middle.application.service.ShipmentApplicationService;
import com.inventory.middle.client.dto.ShipmentDto;
import com.inventory.middle.client.dto.command.ShipmentCommand;
import com.inventory.middle.client.dto.query.ShipmentPageQuery;
import com.inventory.middle.application.convertor.ShipmentDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 交运单Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:28
 */
@Tag(name = " 交运单管理")
@RestController
@RequestMapping("/shipment")
@Slf4j
@CatchAndLog
public class ShipmentController {

    @Resource
    private ShipmentApplicationService shipmentApplicationService;
    @Resource
    private  ShipmentQueryService shipmentQueryService;
    @Resource
    private ShipmentDtoConvertor  shipmentDtoConvertor;


    /**
     * 交运单分页查询
     */
    @Operation(summary="交运单分页查询")
    @PostMapping("/page")
    public PageResponse<ShipmentDto> page(@RequestBody ShipmentPageQuery shipmentPageQuery) {
        return shipmentQueryService.queryPage(shipmentPageQuery);
    }

    /**
     * 交运单list查询
     */
    @Operation(summary="交运单list查询")
    @PostMapping("/list")
            public MultiResponse<ShipmentDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 交运单信息
     */
    @Operation(summary="交运单信息")
    @GetMapping("/find/{id}")
    public SingleResponse<ShipmentDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(shipmentQueryService.findById(id));
    }

    /**
     * 保存交运单
     */
    @Operation(summary="保存交运单")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody ShipmentCommand shipmentCommand) {
        return SingleResponse.buildSuccess(shipmentApplicationService.add(shipmentCommand));

    }

    /**
     * 修改交运单
     */
    @Operation(summary="修改交运单")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody ShipmentCommand shipmentCommand) {
        return SingleResponse.buildSuccess(shipmentApplicationService.update(shipmentCommand));
    }

    /**
     * 删除交运单
     */
    @Operation(summary="删除交运单")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(shipmentApplicationService.deleteBatch(ids));
    }

}