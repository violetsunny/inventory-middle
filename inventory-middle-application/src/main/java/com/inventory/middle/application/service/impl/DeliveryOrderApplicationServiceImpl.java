package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.DeliveryOrderApplicationService;
import com.inventory.middle.domain.service.external.SpDeliveryOrderRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;

/**
 * 备件发货单 ApplicationService 实现（M15：下沉 DeliveryOrderMgntController 的直连外部服务依赖）
 */
@Service
@Slf4j
public class DeliveryOrderApplicationServiceImpl implements DeliveryOrderApplicationService {

    @Resource
    private SpDeliveryOrderRemoteService spDeliveryOrderRemoteService;

    @Override
    public SingleResponse<Object> listSales(Object query) {
        return spDeliveryOrderRemoteService.listSales(query);
    }

    @Override
    public SingleResponse<Object> listPrint(Object query) {
        return spDeliveryOrderRemoteService.listPrint(query);
    }

    @Override
    public SingleResponse<Object> doDeliverPrint(Object request) {
        return spDeliveryOrderRemoteService.doDeliverPrint(request);
    }

    @Override
    public SingleResponse<Object> queryPrintInfos(Object request) {
        return spDeliveryOrderRemoteService.queryPrintInfos(request);
    }

    @Override
    public SingleResponse<Object> listDeliveryState(Object query) {
        return spDeliveryOrderRemoteService.listDeliveryState(query);
    }

    @Override
    public SingleResponse<Object> doConfirmExtraPrint(Object request) {
        return spDeliveryOrderRemoteService.doConfirmExtraPrint(request);
    }
}
