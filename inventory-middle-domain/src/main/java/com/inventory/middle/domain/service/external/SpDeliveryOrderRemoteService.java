package com.inventory.middle.domain.service.external;

import top.kdla.framework.dto.SingleResponse;

/** 备件发货单远程服务（domain 层接口，infra OpenFeign 实现） */
public interface SpDeliveryOrderRemoteService {

    SingleResponse<Object> listSales(Object query);

    SingleResponse<Object> listPrint(Object query);

    SingleResponse<Object> doDeliverPrint(Object request);

    SingleResponse<Object> queryPrintInfos(Object request);

    SingleResponse<Object> listDeliveryState(Object query);

    SingleResponse<Object> doConfirmExtraPrint(Object request);
}
