package com.inventory.middle.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.AccessoriesFlowCodeApplicationService;
import com.inventory.middle.client.code.dto.EnumResponse;
import com.inventory.middle.client.code.dto.request.*;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.client.code.dto.response.FleeingGoodsApplyCheckResponse;
import com.inventory.middle.client.code.dto.response.SpDeliveryDetailPrintInfo;
import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.infra.persistence.entity.CodeDo;
import com.inventory.middle.infra.persistence.entity.ListCodeParamPO;
import com.inventory.middle.infra.persistence.mapper.CodeMapper;
import com.inventory.middle.infra.persistence.repository.impl.CodeRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 备件流转码ApplicationService实现
 * 迁移自: com.enn.inventory.center.ext.biz.code.service.AccessoriesFlowCodeDomainService
 */
@Slf4j
@Service
public class AccessoriesFlowCodeApplicationServiceImpl implements AccessoriesFlowCodeApplicationService {

    @Resource
    private CodeRepositoryImpl codeRepository;

    @Resource
    private CodeMapper codeMapper;

    @Override
    public void manufacturerInStock(ManufacturerInStockRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.manufacturerInStock request={}", JSON.toJSONString(request));
        if (CollectionUtils.isEmpty(request.getItemRequestList())) {
            log.warn("manufacturerInStock: itemRequestList is empty, skip");
            return;
        }
        List<CodeDo> codeList = new ArrayList<>();
        for (BatchCreateCodeItemRequest item : request.getItemRequestList()) {
            int count = item.getCount() != null ? item.getCount() : 0;
            for (int i = 0; i < count; i++) {
                CodeDo codeDo = new CodeDo();
                codeDo.setInnerCode(UUID.randomUUID().toString().replace("-", ""));
                codeDo.setBusinessNo(request.getBusinessNo());
                codeDo.setSourceNo(item.getSourceNo());
                codeDo.setPublisher(item.getPublisher());
                codeDo.setCurrentOwner(item.getCurrentOwner());
                codeDo.setType("MANUFACTURER");
                codeDo.setStatus("UNUSED");
                codeDo.setCreatorId(request.getOperator());
                codeList.add(codeDo);
            }
        }
        if (!codeList.isEmpty()) {
            codeMapper.batchInsert(codeList);
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
        // 1. 按 innerCode 查旧码
        ListCodeParamPO param = new ListCodeParamPO();
        param.setInnerCode(request.getInnerCode());
        List<CodeDo> list = codeMapper.list(param);
        if (list == null || list.isEmpty()) {
            return SingleResponse.buildFailure("NOT_FOUND", "内部码不存在: " + request.getInnerCode());
        }
        CodeDo oldDo = list.get(0);
        // 2. 废弃旧码
        CodeDo scrapped = new CodeDo();
        scrapped.setId(oldDo.getId());
        scrapped.setStatus("SCRAPPED");
        scrapped.setUpdatorId(request.getOperatorId());
        codeMapper.updateByIdSelective(scrapped);
        // 3. 生成新码（继承旧码核心字段，分配新 innerCode）
        CodeDo newDo = new CodeDo();
        BeanUtils.copyProperties(oldDo, newDo);
        newDo.setId(null);
        newDo.setInnerCode(UUID.randomUUID().toString().replace("-", ""));
        newDo.setStatus("UNUSED");
        newDo.setCreatorId(request.getOperatorId());
        newDo.setCreateTime(null);
        newDo.setUpdateTime(null);
        codeMapper.insert(newDo);
        AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
        BeanUtils.copyProperties(newDo, resp);
        log.info("regenerateCode success, oldInnerCode={} newInnerCode={}", request.getInnerCode(), newDo.getInnerCode());
        return SingleResponse.of(resp);
    }

    @Override
    public MultiResponse<FleeingGoodsApplyCheckResponse> fleeingGoodsApplyCheck(FleeingGoodsApplyCheckRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.fleeingGoodsApplyCheck request={}", JSON.toJSONString(request));
        if (CollectionUtils.isEmpty(request.getCodeList())) {
            return MultiResponse.of(new ArrayList<>());
        }
        // 按 code 字段批量查询
        ListCodeParamPO param = new ListCodeParamPO();
        param.setCodeList(request.getCodeList());
        List<CodeDo> codeDoList = codeMapper.list(param);
        List<FleeingGoodsApplyCheckResponse> result = request.getCodeList().stream().map(code -> {
            FleeingGoodsApplyCheckResponse resp = new FleeingGoodsApplyCheckResponse();
            resp.setCode(code);
            CodeDo found = codeDoList.stream()
                    .filter(c -> code.equals(c.getCode())).findFirst().orElse(null);
            if (found == null) {
                resp.setSuccess(false);
                resp.setResultCode("NOT_FOUND");
                resp.setResultMessage("码不存在: " + code);
            } else {
                // 窜货判断：当前持有者 != 请求中 currentOwner
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
        // 按 sourceNo + businessNo 批量更新 currentOwner / logicalPlantNo（发货转移持有者）
        ListCodeParamPO queryParam = new ListCodeParamPO();
        queryParam.setSourceNo(request.getSourceNo());
        queryParam.setBusinessNo(request.getBusinessNo());
        List<CodeDo> codeDoList = codeMapper.list(queryParam);
        if (CollectionUtils.isEmpty(codeDoList)) {
            log.warn("updateCodeForDeliverOrder: no code found for sourceNo={} businessNo={}",
                    request.getSourceNo(), request.getBusinessNo());
            return SingleResponse.of(false);
        }
        List<CodeDo> updateList = codeDoList.stream().map(c -> {
            CodeDo updateDo = new CodeDo();
            updateDo.setId(c.getId());
            updateDo.setCurrentOwner(request.getCurrentOwner());
            updateDo.setExtendField1(request.getLogicalPlantNo());
            updateDo.setUpdatorId(request.getOperatorId());
            return updateDo;
        }).collect(Collectors.toList());
        codeMapper.batchUpdate(updateList);
        log.info("updateCodeForDeliverOrder success, count={} sourceNo={}", updateList.size(), request.getSourceNo());
        return SingleResponse.of(true);
    }

    @Override
    public PageResponse<AccessoriesFlowCodeResponse> manufacturerPageQuery(PageQueryManufacturerCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.manufacturerPageQuery request={}", JSON.toJSONString(request));
        ListCodeParamPO param = new ListCodeParamPO();
        if (request.getBusinessNo() != null) {
            param.setBusinessNo(request.getBusinessNo());
        }
        if (request.getPublisher() != null) {
            param.setPublisher(request.getPublisher());
        }
        if (request.getCurrentOwner() != null) {
            param.setCurrentOwner(request.getCurrentOwner());
        }
        List<AccessoriesFlowCodeResponse> respList = codeMapper.list(param).stream().map(doObj -> {
            AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
            BeanUtils.copyProperties(doObj, resp);
            return resp;
        }).collect(Collectors.toList());
        return PageResponse.of(respList, (long) respList.size(),
                request.getPageSize().longValue(), request.getPageNum().longValue());
    }

    @Override
    public MultiResponse<AccessoriesFlowCodeResponse> listCode(ListAccessoriesFlowCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.listCode request={}", JSON.toJSONString(request));
        ListCodeParamPO param = new ListCodeParamPO();
        BeanUtils.copyProperties(request, param);
        List<AccessoriesFlowCodeResponse> respList = codeMapper.list(param).stream().map(doObj -> {
            AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
            BeanUtils.copyProperties(doObj, resp);
            return resp;
        }).collect(Collectors.toList());
        return MultiResponse.of(respList);
    }

    @Override
    public MultiResponse<AccessoriesFlowCodeResponse> listUnUsedCode(ListUnUsedAccessoriesFlowCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.listUnUsedCode request={}", JSON.toJSONString(request));
        ListCodeParamPO param = new ListCodeParamPO();
        BeanUtils.copyProperties(request, param);
        param.setStatus("UNUSED");
        List<AccessoriesFlowCodeResponse> respList = codeMapper.list(param).stream().map(doObj -> {
            AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
            BeanUtils.copyProperties(doObj, resp);
            return resp;
        }).collect(Collectors.toList());
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
        AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
        BeanUtils.copyProperties(entity, resp);
        return SingleResponse.of(resp);
    }

    @Override
    public SingleResponse<AccessoriesFlowCodeResponse> queryCodeDetailByInnerCode(QueryCodeDetailByInnerCodeRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.queryCodeDetailByInnerCode innerCode={}", request == null ? null : request.getInnerCode());
        if (request == null || request.getInnerCode() == null) {
            return SingleResponse.of(null);
        }
        ListCodeParamPO param = new ListCodeParamPO();
        param.setInnerCode(request.getInnerCode());
        List<com.inventory.middle.infra.persistence.entity.CodeDo> list = codeMapper.list(param);
        if (list == null || list.isEmpty()) {
            return SingleResponse.of(null);
        }
        AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
        BeanUtils.copyProperties(list.get(0), resp);
        return SingleResponse.of(resp);
    }

    @Override
    public MultiResponse<SpDeliveryDetailPrintInfo> queryCodesForPrint(QueryCodesForPrintRequest request) {
        log.info("AccessoriesFlowCodeApplicationServiceImpl.queryCodesForPrint request={}", JSON.toJSONString(request));
        if (CollectionUtils.isEmpty(request.getInnerCodeList())) {
            return MultiResponse.of(new ArrayList<>());
        }
        ListCodeParamPO param = new ListCodeParamPO();
        param.setInnerCodeList(request.getInnerCodeList());
        List<CodeDo> codeDoList = codeMapper.list(param);
        List<SpDeliveryDetailPrintInfo> result = codeDoList.stream().map(codeDo -> {
            SpDeliveryDetailPrintInfo info = new SpDeliveryDetailPrintInfo();
            info.setMarkCode(codeDo.getCode());
            info.setInnerCode(codeDo.getInnerCode());
            info.setMaterialCode(codeDo.getExtendField2());
            info.setManufacturerId(codeDo.getPublisher());
            info.setDistributorId(codeDo.getCurrentOwner());
            return info;
        }).collect(Collectors.toList());
        return MultiResponse.of(result);
    }

    @Override
    public MultiResponse<EnumResponse> accessoriesFlowCodeStatusTypeList() {
        // 返回固定枚举列表: UNUSED / OCCUPIED / USED / SCRAPPED
        List<EnumResponse> list = new ArrayList<>();
        addEnum(list, "UNUSED", "未使用");
        addEnum(list, "OCCUPIED", "已占用");
        addEnum(list, "USED", "已使用");
        addEnum(list, "SCRAPPED", "已报废");
        return MultiResponse.of(list);
    }

    @Override
    public MultiResponse<EnumResponse> codeTypeList() {
        // 返回固定枚举列表: MANUFACTURER / DISTRIBUTOR
        List<EnumResponse> list = new ArrayList<>();
        addEnum(list, "MANUFACTURER", "厂商码");
        addEnum(list, "DISTRIBUTOR", "经销商码");
        return MultiResponse.of(list);
    }

    @SuppressWarnings("unchecked")
    private void addEnum(List<EnumResponse> list, String code, String desc) {
        EnumResponse item = new EnumResponse();
        item.setCode(code);
        item.setDescription(desc);
        list.add(item);
    }
}
