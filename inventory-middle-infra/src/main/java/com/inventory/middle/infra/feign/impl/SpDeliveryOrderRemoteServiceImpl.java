package com.inventory.middle.infra.feign.impl;

import com.inventory.middle.domain.service.external.SpDeliveryOrderRemoteService;
import com.inventory.middle.infra.feign.SpDeliveryOrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

/**
 * 备件发货单远程服务实现（委托 Feign Client，URL 未配置时降级返回错误响应）
 */
@Service
@Slf4j
public class SpDeliveryOrderRemoteServiceImpl implements SpDeliveryOrderRemoteService {

    @Autowired(required = false)
    private SpDeliveryOrderFeignClient feignClient;

    private boolean isAvailable() {
        return feignClient != null;
    }

    @Override
    public SingleResponse<Object> listSales(Object query) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.listSales(query);
    }

    @Override
    public SingleResponse<Object> listPrint(Object query) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.listPrint(query);
    }

    @Override
    public SingleResponse<Object> doDeliverPrint(Object request) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.doDeliverPrint(request);
    }

    @Override
    public SingleResponse<Object> queryPrintInfos(Object request) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.queryPrintInfos(request);
    }

    @Override
    public SingleResponse<Object> listDeliveryState(Object query) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.listDeliveryState(query);
    }

    @Override
    public SingleResponse<Object> doConfirmExtraPrint(Object request) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.doConfirmExtraPrint(request);
    }
}
