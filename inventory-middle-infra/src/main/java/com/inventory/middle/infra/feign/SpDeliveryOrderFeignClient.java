package com.inventory.middle.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.kdla.framework.dto.SingleResponse;

/**
 * 备件发货单外部服务 Feign Client
 * URL 通过 application.yml 配置 remote.spDelivery.url（空串时 Feign 不初始化）
 */
@FeignClient(name = "spDeliveryOrderClient", url = "${remote.spDelivery.url:}")
public interface SpDeliveryOrderFeignClient {

    @PostMapping("/delivery-order/list-sales")
    SingleResponse<Object> listSales(@RequestBody Object query);

    @PostMapping("/delivery-order/list-print")
    SingleResponse<Object> listPrint(@RequestBody Object query);

    @PostMapping("/delivery-order/do-deliver-print")
    SingleResponse<Object> doDeliverPrint(@RequestBody Object request);

    @PostMapping("/delivery-order/query-print-infos")
    SingleResponse<Object> queryPrintInfos(@RequestBody Object request);

    @PostMapping("/delivery-order/list-delivery-state")
    SingleResponse<Object> listDeliveryState(@RequestBody Object query);

    @PostMapping("/delivery-order/do-confirm-extra-print")
    SingleResponse<Object> doConfirmExtraPrint(@RequestBody Object request);
}
