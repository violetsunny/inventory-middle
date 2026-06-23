package com.inventory.middle.interfaces.facade;

import com.inventory.middle.application.service.AccessoriesFlowCodeApplicationService;
import com.inventory.middle.client.code.dto.EnumResponse;
import com.inventory.middle.client.code.dto.request.*;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.client.code.dto.response.FleeingGoodsApplyCheckResponse;
import com.inventory.middle.client.code.dto.response.SpDeliveryDetailPrintInfo;
import com.inventory.middle.client.facade.AccessoriesFlowCodeServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 备件流转码FacadeImpl
 */
@Component
@Slf4j
@CatchAndLog
public class AccessoriesFlowCodeServiceFacadeImpl implements AccessoriesFlowCodeServiceFacade {

    @Resource
    private AccessoriesFlowCodeApplicationService accessoriesFlowCodeApplicationService;

    @Override
    public SingleResponse<Boolean> manufacturerInStock(ManufacturerInStockRequest request) {
        accessoriesFlowCodeApplicationService.manufacturerInStock(request);
        return SingleResponse.of(true);
    }

    @Override
    public SingleResponse<Boolean> occupy(AccessoriesFlowCodeOccupyRequest request) {
        return accessoriesFlowCodeApplicationService.occupy(request);
    }

    @Override
    public SingleResponse<Boolean> use(AccessoriesFlowCodeUseRequest request) {
        return accessoriesFlowCodeApplicationService.use(request);
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> regenerateCode(RegenerateCodeRequest request) {
        return accessoriesFlowCodeApplicationService.regenerateCode(request);
    }

    @Override
    public MultiResponse<FleeingGoodsApplyCheckResponse> fleeingGoodsApplyCheck(FleeingGoodsApplyCheckRequest request) {
        return accessoriesFlowCodeApplicationService.fleeingGoodsApplyCheck(request);
    }

    @Override
    public SingleResponse<Boolean> updateCodeForDeliverOrder(UpdateCodeForDeliverOrderRequest request) {
        return accessoriesFlowCodeApplicationService.updateCodeForDeliverOrder(request);
    }

    @Override
    public PageResponse<AccessoriesFlowCodeResponse> manufacturerPageQuery(PageQueryManufacturerCodeRequest request) {
        return accessoriesFlowCodeApplicationService.manufacturerPageQuery(request);
    }

    @Override
    public MultiResponse<AccessoriesFlowCodeResponse> listCode(ListAccessoriesFlowCodeRequest request) {
        return accessoriesFlowCodeApplicationService.listCode(request);
    }

    @Override
    public MultiResponse<AccessoriesFlowCodeResponse> listUnUsedCode(ListUnUsedAccessoriesFlowCodeRequest request) {
        return accessoriesFlowCodeApplicationService.listUnUsedCode(request);
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetail(QueryAccessoriesFlowCodeDetailRequest request) {
        return accessoriesFlowCodeApplicationService.queryCodeDetail(request);
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetailByInnerCode(QueryCodeDetailByInnerCodeRequest request) {
        return accessoriesFlowCodeApplicationService.queryCodeDetailByInnerCode(request);
    }

    @Override
    public MultiResponse<SpDeliveryDetailPrintInfo> queryCodesForPrint(QueryCodesForPrintRequest request) {
        return accessoriesFlowCodeApplicationService.queryCodesForPrint(request);
    }

    @Override
    public MultiResponse<EnumResponse> accessoriesFlowCodeStatusTypeList() {
        return accessoriesFlowCodeApplicationService.accessoriesFlowCodeStatusTypeList();
    }

    @Override
    public MultiResponse<EnumResponse> codeTypeList() {
        return accessoriesFlowCodeApplicationService.codeTypeList();
    }
}
