package com.inventory.middle.interfaces.web.sparepart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;


/**
 * 备件发货单管理 Controller（从 inventory-center-bff DeliveryOrderMgntController 迁移）
 * 依赖外部系统 SpDeliveryOrderRemoteService（Dubbo），inventory-middle 中尚无对应服务实现。
 * TODO: 待接入 SpDeliveryOrderRemoteService（可通过 OpenFeign 调用）
 */
@Tag(name = "备件发货单管理")
@CatchAndLog
@RestController
@RequestMapping("/delivery-order-mgnt")
@Slf4j
public class DeliveryOrderMgntController {

    @Operation(summary = "查询销售发货单列表")
    @PostMapping("/list-sales")
    public SingleResponse<Object> listSales(@RequestBody Object query) {
        // TODO: 待接入 SpDeliveryOrderRemoteService.listSales()
        log.warn("delivery-order-mgnt/list-sales: 待接入 SpDeliveryOrderRemoteService");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "打印列表")
    @PostMapping("/list-print")
    public SingleResponse<Object> listPrint(@RequestBody Object query) {
        // TODO: 待接入 SpDeliveryOrderRemoteService.listPrint()
        log.warn("delivery-order-mgnt/list-print: 待接入");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "执行发货打印")
    @PostMapping("/do-deliver-print")
    public SingleResponse<Object> doDeliverPrint(@RequestBody Object request) {
        // TODO: 待接入 SpDeliveryOrderRemoteService.doDeliverPrint()
        log.warn("delivery-order-mgnt/do-deliver-print: 待接入");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "查询打印详情")
    @PostMapping("/query-print-infos")
    public SingleResponse<Object> queryPrintInfos(@RequestBody Object request) {
        // TODO: 待接入 SpDeliveryOrderRemoteService.queryPrintInfos()
        log.warn("delivery-order-mgnt/query-print-infos: 待接入");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "查询发货状态列表")
    @PostMapping("/list-delivery-state")
    public SingleResponse<Object> listDeliveryState(@RequestBody Object query) {
        // TODO: 待接入 SpDeliveryOrderRemoteService.listDeliveryState()
        log.warn("delivery-order-mgnt/list-delivery-state: 待接入");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "确认额外打印")
    @PostMapping("/do-confirm-extra-print")
    public SingleResponse<Object> doConfirmExtraPrint(@RequestBody Object request) {
        // TODO: 待接入 SpDeliveryOrderRemoteService.doConfirmExtraPrint()
        log.warn("delivery-order-mgnt/do-confirm-extra-print: 待接入");
        return SingleResponse.buildSuccess(null);
    }
}