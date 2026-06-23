package com.inventory.middle.application.service;

import com.inventory.middle.client.code.dto.EnumResponse;
import com.inventory.middle.client.code.dto.request.*;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.client.code.dto.response.FleeingGoodsApplyCheckResponse;
import com.inventory.middle.client.code.dto.response.SpDeliveryDetailPrintInfo;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 备件流转码ApplicationService
 */
public interface AccessoriesFlowCodeApplicationService {

    void manufacturerInStock(ManufacturerInStockRequest request);

    SingleResponse<Boolean> occupy(AccessoriesFlowCodeOccupyRequest request);

    SingleResponse<Boolean> use(AccessoriesFlowCodeUseRequest request);

    SingleResponse<AccessoriesFlowCodeResponse> regenerateCode(RegenerateCodeRequest request);

    MultiResponse<FleeingGoodsApplyCheckResponse> fleeingGoodsApplyCheck(FleeingGoodsApplyCheckRequest request);

    SingleResponse<Boolean> updateCodeForDeliverOrder(UpdateCodeForDeliverOrderRequest request);

    PageResponse<AccessoriesFlowCodeResponse> manufacturerPageQuery(PageQueryManufacturerCodeRequest request);

    MultiResponse<AccessoriesFlowCodeResponse> listCode(ListAccessoriesFlowCodeRequest request);

    MultiResponse<AccessoriesFlowCodeResponse> listUnUsedCode(ListUnUsedAccessoriesFlowCodeRequest request);

    SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetail(QueryAccessoriesFlowCodeDetailRequest request);

    SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetailByInnerCode(QueryCodeDetailByInnerCodeRequest request);

    MultiResponse<SpDeliveryDetailPrintInfo> queryCodesForPrint(QueryCodesForPrintRequest request);

    MultiResponse<EnumResponse> accessoriesFlowCodeStatusTypeList();

    MultiResponse<EnumResponse> codeTypeList();
}
