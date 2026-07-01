/**
 * kanglele Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import static com.inventory.middle.domain.common.constants.CommonConstant.ZERO;
import static com.inventory.middle.domain.common.util.DateUtils.DATE_FORMAT_COMMON;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.inventory.middle.domain.model.enums.BatchSysEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.service.external.dto.SkuBatchRequest;
import com.inventory.middle.domain.service.external.dto.SkuBatchPageRequest;
import com.inventory.middle.domain.service.external.dto.SkuBatchResponse;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.common.util.DateUtils;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialExtDataBO;
import com.inventory.middle.domain.common.util.MultiThreadingInvokeHelp;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import top.kdla.framework.dto.SingleResponse;

/**
 * 批次同步
 *
 * @author kanglele
 * @version $Id: MaterialBatchSysHandle, v 0.1 2021/8/20 10:58 Exp $
 */
@Slf4j
@Service
public class MaterialBatchSysHandle implements IHandler {
    @Resource
    private RemoteProductCenterRestService remoteProductCenterRestService;
    @Resource
    private ExecutorService inventoryCenterExecutor;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "批次同步")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentIn = req.getMaterialDocumentIn();
        BatchSysEnum batchSysEnum = BatchSysEnum.getByCode(materialDocumentIn.getNeedBatchSys());
        if(Objects.isNull(batchSysEnum) || BatchSysEnum.UN_SYS.equals(batchSysEnum)) {
            log.info("MaterialBatchSysHandle createMaterialDoc 不需要同步{}",materialDocumentIn.getNeedBatchSys());
            return message;
        }
        // 异步执行批次同步
        List<Consumer<MaterialDocumentBO>> consumers = Lists.newArrayList(this::skuBatchSys);
        MultiThreadingInvokeHelp.execute(consumers, materialDocumentIn, inventoryCenterExecutor);
        return message;
    }

    private void skuBatchSys(MaterialDocumentBO materialDocumentIn) {
        try {
            // 批次同步给主数据
            List<SkuBatchRequest> reqs = conversionSkuBatchReq(materialDocumentIn);
            log.info("createMaterialDoc MaterialBatchDataInHandle 批次同步 token:{} 请求:{}", materialDocumentIn.getToken(),JSON.toJSON(reqs));
            SingleResponse result = remoteProductCenterRestService.skuBatchSys(reqs, materialDocumentIn.getToken(), materialDocumentIn.getTenantId());
            log.info("createMaterialDoc MaterialBatchDataInHandle 批次同步 result:{}", JSON.toJSON(result));
        } catch (Exception e) {
            log.error("createMaterialDoc MaterialBatchDataInHandle 批次同步 error", e);
        }

    }

    private List<SkuBatchRequest> conversionSkuBatchReq(MaterialDocumentBO materialDocument) {
        return materialDocument.getMaterialDocumentItems().stream().map(item -> {
            Map<String, Object> extJson = Maps.newHashMap();
            SkuBatchRequest skuBatchReqDTO = new SkuBatchRequest();
            skuBatchReqDTO.setSkuCode(item.getMaterialData().getMaterialCode());
            skuBatchReqDTO.setBatchCode(item.getBatchNo());
            if (checkAnnual(item.getMaterialExtData())) {
                SkuBatchPageRequest skuBatchPageRequest = new SkuBatchPageRequest();
                skuBatchPageRequest.setSkuCode(item.getMaterialData().getMaterialCode());
                skuBatchPageRequest.setBatchCode(item.getBatchNo());
                List<SkuBatchResponse> list =
                    remoteProductCenterRestService.skuBatchListByRequest(skuBatchPageRequest, materialDocument.getToken(),materialDocument.getTenantId());
                if (!CollectionUtils.isEmpty(list)) {
                    SkuBatchResponse detailsResponse = list.get(0);
                    if (Objects.nonNull(detailsResponse) && StringUtils.isNotBlank(detailsResponse.getExt())) {
                        extJson = JSONObject.parseObject(detailsResponse.getExt(), HashMap.class);
                    }
                }
                extJson.put(CommonConstant.ANNUAL_DATE,
                    DateUtils.dateToString(item.getMaterialExtData().getAnnualDate()));
                extJson.put(CommonConstant.ANNUAL_CYCLE_DAYS, item.getMaterialExtData().getAnnualCycleDays());
            }
            skuBatchReqDTO.setBatchNum(item.getQuantityData().getAdjustQuantity().intValue());
            skuBatchReqDTO.setBatchPrice(Optional.ofNullable(item.getQuantityData().getPrice()).isPresent()
                ? item.getQuantityData().getPrice().toPlainString() : ZERO);

            extJson.put(CommonConstant.BATCH_TIME, DateUtils.dateToString(new Date(), DATE_FORMAT_COMMON));
            if (Objects.nonNull(item.getMaterialExtData())) {
                extJson.put(CommonConstant.PRODUCE_DATE, item.getMaterialExtData().getBatchProduceDate());
            }
            skuBatchReqDTO.setExt(JSON.toJSONString(extJson));

            return skuBatchReqDTO;
        }).collect(Collectors.toList());
    }

    private boolean checkAnnual(MaterialExtDataBO materialExtData) {
        if (null == materialExtData) {
            return false;
        }
        if (Objects.isNull(materialExtData.getAnnualDate())) {
            return false;
        }
        if (Objects.isNull(materialExtData.getAnnualCycleDays())) {
            return false;
        }
        return true;
    }

}
