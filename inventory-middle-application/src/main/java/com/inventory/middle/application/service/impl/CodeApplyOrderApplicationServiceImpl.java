package com.inventory.middle.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.CodeApplyOrderApplicationService;
import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderApprovalRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderCreateRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderInfoRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderPageRequest;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderCreateResponse;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderInfoResponse;
import com.inventory.middle.domain.model.entity.CodeApplyOrder;
import com.inventory.middle.domain.model.enums.CodeApprovalStatusEnum;
import com.inventory.middle.application.convertor.CodeApplyOrderConvertor;
import com.inventory.middle.domain.repository.CodeApplyOrderQueryParam;
import com.inventory.middle.domain.repository.CodeApplyOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 码申请单ApplicationService实现
 * 迁移自: com.enn.inventory.center.ext.biz.code.service.impl.CodeApplyOrderBizServiceImpl
 */
@Slf4j
@Service
public class CodeApplyOrderApplicationServiceImpl implements CodeApplyOrderApplicationService {

    @Resource
    private CodeApplyOrderRepository codeApplyOrderRepository;

    @Resource
    private CodeApplyOrderConvertor codeApplyOrderConvertor;

    @Override
    public SingleResponse<CodeApplyOrderCreateResponse> create(CodeApplyOrderCreateRequest createRequest) {
        log.info("CodeApplyOrderApplicationServiceImpl.create request={}", JSON.toJSONString(createRequest));
        CodeApplyOrder entity = codeApplyOrderConvertor.toEntity(createRequest);
        codeApplyOrderRepository.store(entity);
        CodeApplyOrderCreateResponse resp = codeApplyOrderConvertor.toCreateResponse(entity);
        return SingleResponse.of(resp);
    }

    @Override
    public SingleResponse<Boolean> approval(CodeApplyOrderApprovalRequest approvalRequest) {
        log.info("CodeApplyOrderApplicationServiceImpl.approval request={}", JSON.toJSONString(approvalRequest));
        // 1. 校验审批状态合法性（必须是 PASSED=2 或 REFUSED=3）
        if (!CodeApprovalStatusEnum.checkApprovalStatus(approvalRequest.getApprovalStatus())) {
            return SingleResponse.buildFailure("INVALID_STATUS", "审批状态非法，必须为已通过(2)或已拒绝(3)");
        }
        // 2. 查原申请单
        CodeApplyOrder order = codeApplyOrderRepository.findById(approvalRequest.getApplyOrderId());
        if (order == null) {
            return SingleResponse.buildFailure("NOT_FOUND", "申请单不存在");
        }
        // 3. 只有待审批状态（UNAPPROVED=1）可以审批
        if (CodeApprovalStatusEnum.UNAPPROVED.getCode() != order.getApprovalStatus()) {
            return SingleResponse.buildFailure("INVALID_STATE", "申请单当前状态不可审批");
        }
        // 4. 更新审批状态
        order.setApprovalStatus(approvalRequest.getApprovalStatus());
        boolean result = codeApplyOrderRepository.update(order);
        log.info("approval done, applyOrderId={} approvalStatus={} result={}",
                approvalRequest.getApplyOrderId(), approvalRequest.getApprovalStatus(), result);
        return SingleResponse.of(result);
    }

    @Override
    public SingleResponse<CodeApplyOrderInfoResponse> getApplyOrderInfo(CodeApplyOrderInfoRequest infoRequest) {
        log.info("CodeApplyOrderApplicationServiceImpl.getApplyOrderInfo request={}", JSON.toJSONString(infoRequest));
        CodeApplyOrder entity = codeApplyOrderRepository.findById(infoRequest.getApplyOrderId());
        if (entity == null) {
            return SingleResponse.of(null);
        }
        CodeApplyOrderInfoResponse resp = codeApplyOrderConvertor.toInfoResponse(entity);
        return SingleResponse.of(resp);
    }

    @Override
    public PageResponse<CodeApplyOrderDTO> pageList(CodeApplyOrderPageRequest pageRequest) {
        log.info("CodeApplyOrderApplicationServiceImpl.pageList request={}", JSON.toJSONString(pageRequest));
        CodeApplyOrderQueryParam queryParam = codeApplyOrderConvertor.toQueryParam(pageRequest);
        List<CodeApplyOrder> list = codeApplyOrderRepository.listByCondition(queryParam);
        List<CodeApplyOrderDTO> respList = list.stream()
                .map(codeApplyOrderConvertor::toDTO)
                .collect(Collectors.toList());
        return PageResponse.of(respList, (long) respList.size(),
                pageRequest.getPageSize().longValue(), pageRequest.getPageNum().longValue());
    }
}
