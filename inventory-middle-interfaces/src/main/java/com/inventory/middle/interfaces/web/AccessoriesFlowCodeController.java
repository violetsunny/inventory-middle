package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.AccessoriesFlowCodeApplicationService;
import com.inventory.middle.client.code.dto.EnumResponse;
import com.inventory.middle.client.code.dto.request.*;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.client.code.dto.response.FleeingGoodsApplyCheckResponse;
import com.inventory.middle.client.code.dto.response.SpDeliveryDetailPrintInfo;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 备件流转码Controller
 * 迁移自: AccessoriesFlowCodeService
 */
@Tag(name = "备件流转码管理")
@RestController
@RequestMapping("/accessories-flow-code")
@Slf4j
@CatchAndLog
public class AccessoriesFlowCodeController {

    @Resource
    private AccessoriesFlowCodeApplicationService accessoriesFlowCodeApplicationService;

    @Operation(summary = "厂商入库")
    @PostMapping("/manufacturer-in-stock")
    public SingleResponse<Boolean> manufacturerInStock(@RequestBody ManufacturerInStockRequest request) {
        request.setSourceSystem("inventory-middle");
        accessoriesFlowCodeApplicationService.manufacturerInStock(request);
        return SingleResponse.of(true);
    }

    @Operation(summary = "备件流转码占用")
    @PostMapping("/occupy")
    public SingleResponse<Boolean> occupy(@RequestBody AccessoriesFlowCodeOccupyRequest request) {
        request.setOperatorId(UserContextHolder.getUserId());
        request.setSourceSystem("inventory-middle");
        return accessoriesFlowCodeApplicationService.occupy(request);
    }

    @Operation(summary = "备件流转码使用")
    @PostMapping("/use")
    public SingleResponse<Boolean> use(@RequestBody AccessoriesFlowCodeUseRequest request) {
        request.setOperatorId(UserContextHolder.getUserId());
        request.setSourceSystem("inventory-middle");
        return accessoriesFlowCodeApplicationService.use(request);
    }

    @Operation(summary = "重新生成备件流转码")
    @PostMapping("/regenerate-code")
    public SingleResponse<AccessoriesFlowCodeResponse> regenerateCode(@RequestBody RegenerateCodeRequest request) {
        request.setOperatorId(UserContextHolder.getUserId());
        request.setSourceSystem("inventory-middle");
        return accessoriesFlowCodeApplicationService.regenerateCode(request);
    }

    @Operation(summary = "窜货申请前校验")
    @PostMapping("/fleeing-goods-apply-check")
    public MultiResponse<FleeingGoodsApplyCheckResponse> fleeingGoodsApplyCheck(@RequestBody FleeingGoodsApplyCheckRequest request) {
        return accessoriesFlowCodeApplicationService.fleeingGoodsApplyCheck(request);
    }

    @Operation(summary = "更新备件流转码上附带的逻辑仓信息")
    @PostMapping("/update-code-for-deliver-order")
    public SingleResponse<Boolean> updateCodeForDeliverOrder(@RequestBody UpdateCodeForDeliverOrderRequest request) {
        request.setOperatorId(UserContextHolder.getUserId());
        request.setSourceSystem("inventory-middle");
        return accessoriesFlowCodeApplicationService.updateCodeForDeliverOrder(request);
    }

    @Operation(summary = "厂商分页查询码")
    @PostMapping("/manufacturer-page-query")
    public PageResponse<AccessoriesFlowCodeResponse> manufacturerPageQuery(@RequestBody PageQueryManufacturerCodeRequest request) {
        return accessoriesFlowCodeApplicationService.manufacturerPageQuery(request);
    }

    @Operation(summary = "查询备件流转码列表")
    @PostMapping("/list-code")
    public MultiResponse<AccessoriesFlowCodeResponse> listCode(@RequestBody ListAccessoriesFlowCodeRequest request) {
        return accessoriesFlowCodeApplicationService.listCode(request);
    }

    @Operation(summary = "查询未使用状态的码列表")
    @PostMapping("/list-un-used-code")
    public MultiResponse<AccessoriesFlowCodeResponse> listUnUsedCode(@RequestBody ListUnUsedAccessoriesFlowCodeRequest request) {
        return accessoriesFlowCodeApplicationService.listUnUsedCode(request);
    }

    @Operation(summary = "查询单个码的详情信息")
    @PostMapping("/query-code-detail")
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetail(@RequestBody QueryAccessoriesFlowCodeDetailRequest request) {
        return accessoriesFlowCodeApplicationService.queryCodeDetail(request);
    }

    @Operation(summary = "通过内部码查询详情")
    @PostMapping("/query-code-detail-by-inner-code")
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetailByInnerCode(@RequestBody QueryCodeDetailByInnerCodeRequest request) {
        return accessoriesFlowCodeApplicationService.queryCodeDetailByInnerCode(request);
    }

    @Operation(summary = "查询码信息用来打印")
    @PostMapping("/query-codes-for-print")
    public MultiResponse<SpDeliveryDetailPrintInfo> queryCodesForPrint(@RequestBody QueryCodesForPrintRequest request) {
        request.setOperatorId(UserContextHolder.getUserId());
        return accessoriesFlowCodeApplicationService.queryCodesForPrint(request);
    }

    @Operation(summary = "备件流转码状态枚举列表")
    @GetMapping("/status-type-list")
    public MultiResponse<EnumResponse> accessoriesFlowCodeStatusTypeList() {
        return accessoriesFlowCodeApplicationService.accessoriesFlowCodeStatusTypeList();
    }

    @Operation(summary = "码类型枚举列表")
    @GetMapping("/code-type-list")
    public MultiResponse<EnumResponse> codeTypeList() {
        return accessoriesFlowCodeApplicationService.codeTypeList();
    }
}