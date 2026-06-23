package com.inventory.middle.client.code.service;


import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import com.inventory.middle.client.code.dto.EnumResponse;
import com.inventory.middle.client.code.dto.request.*;
import com.inventory.middle.client.code.dto.response.FleeingGoodsApplyCheckResponse;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.client.code.dto.response.SpDeliveryDetailPrintInfo;

import java.util.ArrayList;

/**
 * 备件流转码服务
 *
 * @author hjs
 * @date 2021/12/14
 */
public interface AccessoriesFlowCodeService {

    /**
     * 厂商入库
     * @param request
     * @return
     */
    SingleResponse<Object> manufacturerInStock(ManufacturerInStockRequest request);

    /**
     * 备件流转码 占用
     * @param request
     * @return
     */
    SingleResponse<Object> occupy(AccessoriesFlowCodeOccupyRequest request);

    /**
     * 备件流转码 使用
     * @param request
     * @return
     */
    SingleResponse<Object> use(AccessoriesFlowCodeUseRequest request);

    /**
     * 重新生成备件流转码
     * @param request
     * @return
     */
    SingleResponse<AccessoriesFlowCodeResponse> regenerateCode(RegenerateCodeRequest request);

    /**
     * 窜货申请前校验
     * @return
     */
    SingleResponse<ArrayList<FleeingGoodsApplyCheckResponse>> fleeingGoodsApplyCheck(FleeingGoodsApplyCheckRequest request);

    /**
     * 更新备件流转码上 附带的逻辑仓信息
     * @return
     */
    SingleResponse<Object> updateCodeForDeliverOrder(UpdateCodeForDeliverOrderRequest request);

    /**
     * 厂商分页查询 码
     * @param request
     * @return
     */
    PageResponse<AccessoriesFlowCodeResponse> manufacturerPageQuery(PageQueryManufacturerCodeRequest request);

    /**
     * 查询备件流转码列表
     * @param request
     * @return
     */
    SingleResponse<ArrayList<AccessoriesFlowCodeResponse>> listCode(ListAccessoriesFlowCodeRequest request);

    /**
     * 查询未使用状态的码列表
     * @param request
     * @return
     */
    SingleResponse<ArrayList<AccessoriesFlowCodeResponse>> listUnUsedCode(ListUnUsedAccessoriesFlowCodeRequest request);
    /**
     * 查询单个码的详情信息
     * @param request
     * @return
     */
    SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetail(QueryAccessoriesFlowCodeDetailRequest request);

    /**
     * 通过内部码查询详情
     * @param request
     * @return
     */
    SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetailByInnerCode(QueryCodeDetailByInnerCodeRequest request);

    /**
     * 查询码信息，用来打印
     * @param request
     * @return
     */
    SingleResponse<ArrayList<SpDeliveryDetailPrintInfo>> queryCodesForPrint(QueryCodesForPrintRequest request);

    /**
     * 备件流转码，状态枚举列表
     * @return
     */
    SingleResponse<ArrayList<EnumResponse<String>>> accessoriesFlowCodeStatusTypeList();

    /**
     * 码类型枚举列表
     * @return
     */
    SingleResponse<ArrayList<EnumResponse<String>>> codeTypeList();


}
