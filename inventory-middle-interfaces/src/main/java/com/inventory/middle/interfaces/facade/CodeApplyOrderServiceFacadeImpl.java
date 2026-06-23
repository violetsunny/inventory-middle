package com.inventory.middle.interfaces.facade;

import com.inventory.middle.application.service.CodeApplyOrderApplicationService;
import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderApprovalRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderCreateRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderInfoRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderPageRequest;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderCreateResponse;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderInfoResponse;
import com.inventory.middle.client.facade.CodeApplyOrderServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 码申请单FacadeImpl
 */
@Component
@Slf4j
@CatchAndLog
public class CodeApplyOrderServiceFacadeImpl implements CodeApplyOrderServiceFacade {

    @Resource
    private CodeApplyOrderApplicationService codeApplyOrderApplicationService;

    @Override
    public SingleResponse<CodeApplyOrderCreateResponse> create(CodeApplyOrderCreateRequest createRequest) {
        return codeApplyOrderApplicationService.create(createRequest);
    }

    @Override
    public SingleResponse<Boolean> approval(CodeApplyOrderApprovalRequest approvalRequest) {
        return codeApplyOrderApplicationService.approval(approvalRequest);
    }

    @Override
    public SingleResponse<CodeApplyOrderInfoResponse> getApplyOrderInfo(CodeApplyOrderInfoRequest infoRequest) {
        return codeApplyOrderApplicationService.getApplyOrderInfo(infoRequest);
    }

    @Override
    public PageResponse<CodeApplyOrderDTO> pageList(CodeApplyOrderPageRequest pageRequest) {
        return codeApplyOrderApplicationService.pageList(pageRequest);
    }
}
