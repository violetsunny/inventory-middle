package com.inventory.middle.interfaces.web.sparepart;

import com.inventory.middle.domain.service.external.SpDeliveryOrderRemoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;


/**
 * 备件发货单管理 Controller（从 inventory-center-bff DeliveryOrderMgntController 迁移）
 * 依赖外部系统 SpDeliveryOrderRemoteService（OpenFeign），URL 未配置时降级返回错误响应。
 */
@Tag(name = "备件发货单管理")
@CatchAndLog
@RestController
@RequestMapping("/delivery-order-mgnt")
@Slf4j
public class DeliveryOrderMgntController {

    @Resource
    private SpDeliveryOrderRemoteService spDeliveryOrderRemoteService;

    @Operation(summary = "查询销售发货单列表")
    @PostMapping("/list-sales")
    public SingleResponse<Object> listSales(@RequestBody Object query) {
        return spDeliveryOrderRemoteService.listSales(query);
    }

    @Operation(summary = "打印列表")
    @PostMapping("/list-print")
    public SingleResponse<Object> listPrint(@RequestBody Object query) {
        return spDeliveryOrderRemoteService.listPrint(query);
    }

    @Operation(summary = "执行发货打印")
    @PostMapping("/do-deliver-print")
    public SingleResponse<Object> doDeliverPrint(@RequestBody Object request) {
        return spDeliveryOrderRemoteService.doDeliverPrint(request);
    }

    @Operation(summary = "查询打印详情")
    @PostMapping("/query-print-infos")
    public SingleResponse<Object> queryPrintInfos(@RequestBody Object request) {
        return spDeliveryOrderRemoteService.queryPrintInfos(request);
    }

    @Operation(summary = "查询发货状态列表")
    @PostMapping("/list-delivery-state")
    public SingleResponse<Object> listDeliveryState(@RequestBody Object query) {
        return spDeliveryOrderRemoteService.listDeliveryState(query);
    }

    @Operation(summary = "确认额外打印")
    @PostMapping("/do-confirm-extra-print")
    public SingleResponse<Object> doConfirmExtraPrint(@RequestBody Object request) {
        return spDeliveryOrderRemoteService.doConfirmExtraPrint(request);
    }
}
