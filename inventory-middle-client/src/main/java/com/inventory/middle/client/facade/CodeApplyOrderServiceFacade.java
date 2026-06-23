package com.inventory.middle.client.facade;

import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderApprovalRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderCreateRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderInfoRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderPageRequest;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderCreateResponse;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderInfoResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 码申请单Facade接口
 */
public interface CodeApplyOrderServiceFacade {

    SingleResponse<CodeApplyOrderCreateResponse> create(CodeApplyOrderCreateRequest createRequest);

    SingleResponse<Boolean> approval(CodeApplyOrderApprovalRequest approvalRequest);

    SingleResponse<CodeApplyOrderInfoResponse> getApplyOrderInfo(CodeApplyOrderInfoRequest infoRequest);

    PageResponse<CodeApplyOrderDTO> pageList(CodeApplyOrderPageRequest pageRequest);
}
