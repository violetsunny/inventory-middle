package com.inventory.middle.domain.service.external;

import com.inventory.middle.client.dto.sku.SkuBatchPageRequest;
import com.inventory.middle.client.dto.sku.SkuBatchResponse;
import com.inventory.middle.domain.service.external.dto.SkuBatchRequest;
import com.inventory.middle.domain.service.external.dto.SkuResponse;
import top.kdla.framework.dto.SingleResponse;
import java.util.List;

/** 产品中心远程服务接口（domain 层定义，infra 层实现） */
public interface RemoteProductCenterRestService {

    SingleResponse<Object> skuBatchSys(List<SkuBatchRequest> reqs, String token, String tenantId);

    List<com.inventory.middle.domain.service.external.dto.SkuBatchResponse> skuBatchListByRequest(
        com.inventory.middle.domain.service.external.dto.SkuBatchPageRequest request, String token, String tenantId);

    List<SkuResponse> skuListByRequest(List<String> skuCodes, String token, String tenantId);

    /** skuBatch 带 special token 查询（用于年度盘点）*/
    List<SkuBatchResponse> skuBatchListByRequestWithSpecialToken(
        SkuBatchPageRequest request, String token, String operatorId, String tenantId);
}
