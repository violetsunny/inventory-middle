package com.inventory.middle.interfaces.web.bff;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import java.util.Collections;

@Tag(name = "BFF 兼容：交运单查询")
@CatchAndLog
@RestController
@RequestMapping("/shipmentQuery/v1")
@Slf4j
public class ShipmentQueryBffController {

    @Operation(summary = "分页查询交运单信息（BFF 兼容 stub）")
    @PostMapping("/query")
    public PageResponse<Object> queryShipment() {
        log.warn("BFF 兼容路由 /shipmentQuery/v1/query：源 BFF 返回空 stub，middle 暂未实现真实逻辑");
        return PageResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "交运单详情信息查询（BFF 兼容 stub）")
    @PostMapping("/detail/query")
    public SingleResponse<Object> queryShipmentDetail() {
        log.warn("BFF 兼容路由 /shipmentQuery/v1/detail/query：源 BFF 返回空 stub，middle 暂未实现真实逻辑");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "交运单类型查询（BFF 兼容 stub）")
    @GetMapping("/type/query")
    public SingleResponse<Object> queryShipmentType(@RequestParam(required = false) Long tenantId) {
        log.warn("BFF 兼容路由 /shipmentQuery/v1/type/query：源 BFF 返回空 stub，middle 暂未实现真实逻辑");
        return SingleResponse.buildSuccess(null);
    }
}
