package com.inventory.middle.client.code.service;


import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderApprovalRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderCreateRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderInfoRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderPageRequest;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderCreateResponse;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderInfoResponse;


/**
 * 申请窜货单的service
 *
 * @author dongguo.tao
 * @date 2021-12-16 15:23:52
 */
public interface CodeApplyOrderService {

    /**
     * 码申请单创建
     * @param createRequest
     * @return
     */
    SingleResponse<CodeApplyOrderCreateResponse> create(CodeApplyOrderCreateRequest createRequest);

    /**
     * 申请单审批
     * @param approvalRequest
     * @return
     */
    SingleResponse<Boolean> approval(CodeApplyOrderApprovalRequest approvalRequest);

    /**
     * 申请单信息
     * @param infoRequest
     * @return
     */
    SingleResponse<CodeApplyOrderInfoResponse> getApplyOrderInfo(CodeApplyOrderInfoRequest infoRequest);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    PageResponse<CodeApplyOrderDTO> pageList(CodeApplyOrderPageRequest pageRequest);


}
