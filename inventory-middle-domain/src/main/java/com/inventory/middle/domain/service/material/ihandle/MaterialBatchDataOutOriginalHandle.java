/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import static com.inventory.middle.domain.common.constants.CommonConstant.DEC;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import com.inventory.middle.domain.service.external.converter.MaterialDocBizConverter;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.impl.LogicalPlantCoreService;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;

import lombok.extern.slf4j.Slf4j;

/**
 * 入库数据组装  原有批次
 *
 * @author kll
 * @version $Id: MaterialDocBizHandler, v 0.1 2021/6/20 20:06 Exp $
 */
@Service
@Slf4j
public class MaterialBatchDataOutOriginalHandle implements IHandler {

    @Resource
    private MaterialDocBizConverter materialDocBizConverter;
    @Resource
    private LogicalPlantCoreService logicalPlantCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "出库数据组装")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocument = req.getMaterialDocument();
        transferItemBOs(materialDocument);
        //生成出对象
        MaterialDocumentBO materialDocumentOut = materialDocBizConverter.copyMaterialDocumentIn(materialDocument);
        //出数据合并 sku-logical-locations-batch-stockType
        mergeItemBO(materialDocumentOut);
        //放入请求中
        req.setMaterialDocumentOut(materialDocumentOut);
        log.info("createMaterialDoc MaterialBatchDataOutOriginalHandle 补充后的{},合并后的{}", JSON.toJSONString(materialDocument),JSON.toJSONString(materialDocumentOut));
        return message;
    }

    private void mergeItemBO(MaterialDocumentBO materialDocumentOut) {
        Map<String, List<MaterialDocumentItemBO>> itemGroupBy = materialDocumentOut.getMaterialDocumentItems().stream().collect(Collectors.groupingBy(
            d -> new StringJoiner(DEC).add(d.getMaterialData().getMaterialCode()).add(d.getWarehouseData().getDemandLogicalPlantNo())
                .add(d.getWarehouseData().getDemandStorageLocationNo()).add(d.getWarehouseData().getDemandBatchNo())
                .add(String.valueOf(d.getWarehouseData().getDemandStockType()))
                .toString()));

        List<MaterialDocumentItemBO> itemBOS = itemGroupBy.entrySet().stream().map(entry -> {
            MaterialDocumentItemBO itemNew = entry.getValue().get(0);
            BigDecimal adjustQuantity = entry.getValue().stream().map(d -> d.getQuantityData().getAdjustQuantity()).reduce(BigDecimal::add).get();
            itemNew.getQuantityData().setAdjustQuantity(adjustQuantity);
            return itemNew;
        }).collect(Collectors.toList());

        materialDocumentOut.setMaterialDocumentItems(itemBOS);
    }

    private void transferItemBOs(MaterialDocumentBO materialDocument) {
        LogicalPlantBO logicalPlantDo = logicalPlantCoreService.getByLogicalPlantNo(materialDocument.getDemandLogicalPlantNo());
        if (logicalPlantDo == null) {
            throw new BusinessException(ResponseCodeEnum.LOGICAL_PLANT_NOT_EXIST, materialDocument.getDemandLogicalPlantNo());
        }
        for (MaterialDocumentItemBO itemBO : materialDocument.getMaterialDocumentItems()) {
            itemBO.getWarehouseData().setAdjustType(materialDocument.getAdjustType());
            itemBO.getWarehouseData().setIo(materialDocument.getIo());
            itemBO.getWarehouseData().setDemandLogicalPlantNo(logicalPlantDo.getLogicalPlantNo());
            itemBO.getWarehouseData().setDemandLogicalPlantName(logicalPlantDo.getLogicalPlantName());
            itemBO.getWarehouseData()
                .setDemandStockType(Optional.ofNullable(itemBO.getWarehouseData().getDemandStockType()).orElse(StockTypeEnum.UNRESTRICTED.getCode()));

        }
    }
}
