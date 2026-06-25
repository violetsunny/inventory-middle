package com.inventory.middle.application.service;

import top.kdla.framework.dto.SingleResponse;

/**
 * 备件发货单 ApplicationService（M15：下沉 DeliveryOrderMgntController 的直连外部服务依赖）
 */
public interface DeliveryOrderApplicationService {

    SingleResponse<Object> listSales(Object query);

    SingleResponse<Object> listPrint(Object query);

    SingleResponse<Object> doDeliverPrint(Object request);

    SingleResponse<Object> queryPrintInfos(Object request);

    SingleResponse<Object> listDeliveryState(Object query);

    SingleResponse<Object> doConfirmExtraPrint(Object request);
}
