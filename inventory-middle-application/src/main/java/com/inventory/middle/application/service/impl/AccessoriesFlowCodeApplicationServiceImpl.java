package com.inventory.middle.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.convertor.AccessoriesFlowCodeConvertor;
import com.inventory.middle.application.service.AccessoriesFlowCodeApplicationService;
import com.inventory.middle.client.code.dto.EnumResponse;
import com.inventory.middle.client.code.dto.request.*;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.client.code.dto.response.FleeingGoodsApplyCheckResponse;
import com.inventory.middle.client.code.dto.response.SpDeliveryDetailPrintInfo;
import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.domain.repository.CodeQueryParam;
import com.inventory.middle.domain.repository.CodeRepository;
import com.inventory.middle.domain.service.CodeDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 备件流转码ApplicationService实现
 * 迁移自: com.enn.inventory.center.ext.biz.code.service.AccessoriesFlowCodeDomainService
 */
@Slf4j
@Service
public class AccessoriesFlowCodeApplicationServiceImpl implements AccessoriesFlowCodeApplicationService {

    @Resource
    private CodeRepository codeRepository;

    @Resource
    private CodeDomainService codeDomainService;

    @Resource
    private AccessoriesFlowCodeConvertor accessoriesFlowCodeConvertor;

    @Override
    public void manufacturerInStock(ManufacturerInStockRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.manufacturerInStock request={}", JSON.toJSONString(request));
        if (CollectionUtils.isEmpty(request.getItemRequestList())) {
            log.warn("manufacturerInStock: itemRequestList is empty, skip");
            return;
        }
        List<Code> codeList = new ArrayList<>();
        for (BatchCreateCodeItemRequest item : request.getItemRequestList()) {
            int count = item.getCount() != null ? item.getCount() : 0;
            for (int i = 0; i < count; i++) {
                Code code = new Code();
                code.setBusinessNo(request.getBusinessNo());
                code.setSourceNo(item.getSourceNo());
                code.setPublisher(item.getPublisher());
                code.setCurrentOwner(item.getCurrentOwner());
                code.setType("MANUFACTURER");
                code.setStatus("UNUSED");
                code.setCreatorId(request.getOperator());
                codeList.add(code);
            }
        }
        if (!codeList.isEmpty()) {
            codeDomainService.manufacturerInStock(request.getBusinessNo(), codeList);
            log.info("manufacturerInStock success, total={} businessNo={}", codeList.size(), request.getBusinessNo());
        }
    }

    @Override
    public SingleResponse<Boolean> occupy(AccessoriesFlowCodeOccupyRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.occupy code={}", request == null ? null : request.getAccessoriesFlowCode());
        if (request == null || request.getAccessoriesFlowCode() == null) {
            return SingleResponse.of(false);
        }
        Code entity = codeRepository.findByCode(request.getAccessoriesFlowCode());
        if (entity == null) {
            return SingleResponse.of(false);
        }
        entity.setStatus("OCCUPIED");
        return SingleResponse.of(codeRepository.update(entity));
    }

    @Override
    public SingleResponse<Boolean> use(AccessoriesFlowCodeUseRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.use code={}", request == null ? null : request.getAccessoriesFlowCode());
        if (request == null || request.getAccessoriesFlowCode() == null) {
            return SingleResponse.of(false);
        }
        Code entity = codeRepository.findByCode(request.getAccessoriesFlowCode());
        if (entity == null) {
            return SingleResponse.of(false);
        }
        entity.setStatus("USED");
        return SingleResponse.of(codeRepository.update(entity));
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> regenerateCode(RegenerateCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.regenerateCode request={}", JSON.toJSONString(request));
        Code newCode = codeDomainService.regenerateCode(request.getInnerCode(), request.getOperatorId());
        if (newCode == null) {
            return SingleResponse.buildFailure("NOT_FOUND", "内部码不存在: " + request.getInnerCode());
        }
        AccessoriesFlowCodeResponse resp = accessoriesFlowCodeConvertor.toResponse(newCode);
        log.info("regenerateCode success, oldInnerCode={} newInnerCode={}", request.getInnerCode(), newCode.getInnerCode());
        return SingleResponse.of(resp);
    }

    @Override
    public MultiResponse<FleeingGoodsApplyCheckResponse> fleeingGoodsApplyCheck(FleeingGoodsApplyCheckRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.fleeingGoodsApplyCheck request={}", JSON.toJSONString(request));
        if (CollectionUtils.isEmpty(request.getCodeList())) {
            return MultiResponse.of(new ArrayList<>());
        }
        List<Code> codeList = codeRepository.list(
                CodeQueryParam.of().withCodeList(request.getCodeList()));
        List<FleeingGoodsApplyCheckResponse> result = request.getCodeList().stream().map(code -> {
            FleeingGoodsApplyCheckResponse resp = new FleeingGoodsApplyCheckResponse();
            resp.setCode(code);
            Code found = codeList.stream()
                    .filter(c -> code.equals(c.getCode())).findFirst().orElse(null);
            if (found == null) {
                resp.setSuccess(false);
                resp.setResultCode("NOT_FOUND");
                resp.setResultMessage("码不存在: " + code);
            } else {
                boolean isFleeing = !request.getCurrentOwner().equals(found.getCurrentOwner());
                resp.setSuccess(!isFleeing);
                resp.setResultCode(isFleeing ? "FLEEING" : "OK");
                resp.setResultMessage(isFleeing ? "窜货: 持有者不匹配" : "校验通过");
            }
            return resp;
        }).collect(Collectors.toList());
        return MultiResponse.of(result);
    }

    @Override
    public SingleResponse<Boolean> updateCodeForDeliverOrder(UpdateCodeForDeliverOrderRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.updateCodeForDeliverOrder request={}", JSON.toJSONString(request));
        List<Code> codeList = codeRepository.listBySourceAndBusiness(
                request.getSourceNo(), request.getBusinessNo());
        if (CollectionUtils.isEmpty(codeList)) {
            log.warn("updateCodeForDeliverOrder: no code found for sourceNo={} businessNo={}",
                    request.getSourceNo(), request.getBusinessNo());
            return SingleResponse.of(false);
        }
        List<Code> updateList = codeList.stream().map(c -> {
            Code updateCode = new Code();
            updateCode.setId(c.getId());
            updateCode.setCurrentOwner(request.getCurrentOwner());
            updateCode.setExtendField1(request.getLogicalPlantNo());
            updateCode.setUpdatorId(request.getOperatorId());
            return updateCode;
        }).collect(Collectors.toList());
        codeRepository.batchUpdate(updateList);
        log.info("updateCodeForDeliverOrder success, count={} sourceNo={}", updateList.size(), request.getSourceNo());
        return SingleResponse.of(true);
    }

    @Override
    public PageResponse<AccessoriesFlowCodeResponse> manufacturerPageQuery(PageQueryManufacturerCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.manufacturerPageQuery request={}", JSON.toJSONString(request));
        CodeQueryParam param = CodeQueryParam.of();
        if (request.getBusinessNo() != null) {
            param.setBusinessNo(request.getBusinessNo());
        }
        if (request.getPublisher() != null) {
            param.setPublisher(request.getPublisher());
        }
        if (request.getCurrentOwner() != null) {
            param.setCurrentOwner(request.getCurrentOwner());
        }
        List<AccessoriesFlowCodeResponse> respList = codeRepository.list(param).stream()
                .map(accessoriesFlowCodeConvertor::toResponse)
                .collect(Collectors.toList());
        return PageResponse.of(respList, (long) respList.size(),
                request.getPageSize().longValue(), request.getPageNum().longValue());
    }

    @Override
    public MultiResponse<AccessoriesFlowCodeResponse> listCode(ListAccessoriesFlowCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.listCode request={}", JSON.toJSONString(request));
        CodeQueryParam param = accessoriesFlowCodeConvertor.toQueryParam(request);
        List<AccessoriesFlowCodeResponse> respList = codeRepository.list(param).stream()
                .map(accessoriesFlowCodeConvertor::toResponse)
                .collect(Collectors.toList());
        return MultiResponse.of(respList);
    }

    @Override
    public MultiResponse<AccessoriesFlowCodeResponse> listUnUsedCode(ListUnUsedAccessoriesFlowCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.listUnUsedCode request={}", JSON.toJSONString(request));
        CodeQueryParam param = accessoriesFlowCodeConvertor.toQueryParam(request);
        param.setStatus("UNUSED");
        List<AccessoriesFlowCodeResponse> respList = codeRepository.list(param).stream()
                .map(accessoriesFlowCodeConvertor::toResponse)
                .collect(Collectors.toList());
        return MultiResponse.of(respList);
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetail(QueryAccessoriesFlowCodeDetailRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.queryCodeDetail code={}", request == null ? null : request.getCode());
        if (request == null || request.getCode() == null) {
            return SingleResponse.of(null);
        }
        Code entity = codeRepository.findByCode(request.getCode());
        if (entity == null) {
            return SingleResponse.of(null);
        }
        AccessoriesFlowCodeResponse resp = accessoriesFlowCodeConvertor.toResponse(entity);
        return SingleResponse.of(resp);
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetailByInnerCode(QueryCodeDetailByInnerCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.queryCodeDetailByInnerCode innerCode={}", request == null ? null : request.getInnerCode());
        if (request == null || request.getInnerCode() == null) {
            return SingleResponse.of(null);
        }
        Code entity = codeRepository.findByInnerCode(request.getInnerCode());
        if (entity == null) {
            return SingleResponse.of(null);
        }
        AccessoriesFlowCodeResponse resp = accessoriesFlowCodeConvertor.toResponse(entity);
        return SingleResponse.of(resp);
    }

    @Override
    public MultiResponse<SpDeliveryDetailPrintInfo> queryCodesForPrint(QueryCodesForPrintRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.queryCodesForPrint request={}", JSON.toJSONString(request));
        if (CollectionUtils.isEmpty(request.getInnerCodeList())) {
            return MultiResponse.of(new ArrayList<>());
        }
        List<Code> codeList = codeRepository.listByInnerCodes(request.getInnerCodeList());
        List<SpDeliveryDetailPrintInfo> result = codeList.stream().map(code -> {
            SpDeliveryDetailPrintInfo info = new SpDeliveryDetailPrintInfo();
            info.setMarkCode(code.getCode());
            info.setInnerCode(code.getInnerCode());
            info.setMaterialCode(code.getExtendField2());
            info.setManufacturerId(code.getPublisher());
            info.setDistributorId(code.getCurrentOwner());
            return info;
        }).collect(Collectors.toList());
        return MultiResponse.of(result);
    }

    @Override
    public MultiResponse<EnumResponse> accessoriesFlowCodeStatusTypeList() {
        List<EnumResponse> list = new ArrayList<>();
        addEnum(list, "UNUSED", "未使用");
        addEnum(list, "OCCUPIED", "已占用");
        addEnum(list, "USED", "已使用");
        addEnum(list, "SCRAPPED", "已报废");
        return MultiResponse.of(list);
    }

    @Override
    public MultiResponse<EnumResponse> codeTypeList() {
        List<EnumResponse> list = new ArrayList<>();
        addEnum(list, "MANUFACTURER", "厂商码");
        addEnum(list, "DISTRIBUTOR", "经销商码");
        return MultiResponse.of(list);
    }

    private void addEnum(List<EnumResponse> list, String code, String desc) {
        EnumResponse item = new EnumResponse();
        item.setCode(code);
        item.setDescription(desc);
        list.add(item);
    }
}
