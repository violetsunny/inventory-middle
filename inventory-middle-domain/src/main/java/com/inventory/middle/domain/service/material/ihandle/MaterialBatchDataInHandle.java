/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.external.converter.MaterialDocBizConverter;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.model.enums.StorageLocationTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.EnumMapping;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import com.inventory.middle.domain.service.impl.LogicalPlantCoreService;
import com.inventory.middle.domain.service.impl.StorageLocationCoreService;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.DEC;
import static com.inventory.middle.domain.common.constants.CommonConstant.EMPTY;

/**
 * 入库数据组装  新批次同步
 *
 * @author kll
 * @version $Id: MaterialDocBizHandler, v 0.1 2021/6/20 20:06 Exp $
 */
@Service
@Slf4j
public class MaterialBatchDataInHandle implements IHandler {

    @Resource
    private LogicalPlantCoreService logicalPlantCoreService;
    @Resource
    private StorageLocationCoreService storageLocationCoreService;
    @Resource
    private MaterialDocBizConverter materialDocBizConverter;
    @Resource
    private MaterialDocCoreService materialDocCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "入库数据组装")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocument = req.getMaterialDocument();
        //先进行数据转换是为了对原先的item进行默认赋值，方便后续的使用
        transferItemBOs(materialDocument);
        //生成入对象
        MaterialDocumentBO materialDocumentIn = materialDocBizConverter.copyMaterialDocumentIn(materialDocument);
        //数据合并 sku-logical-location-stockType
        mergeItemBO(materialDocumentIn);
        //放入请求中
        req.setMaterialDocumentIn(materialDocumentIn);
        log.info("createMaterialDoc MaterialBatchDataInHandle 补充后的{},合并后的{}", JSON.toJSONString(materialDocument),JSON.toJSONString(materialDocumentIn));
        return message;
    }

    private void mergeItemBO(MaterialDocumentBO materialDocumentIn) {
        Map<String, List<MaterialDocumentItemBO>> itemGroupBy = materialDocumentIn.getMaterialDocumentItems().stream().collect(Collectors.groupingBy(
            d -> new StringJoiner(DEC).add(d.getMaterialData().getMaterialCode()).add(d.getWarehouseData().getSupplyLogicalPlantNo())
                .add(d.getWarehouseData().getSupplyStorageLocationNo()).add(String.valueOf(d.getWarehouseData().getSupplyStockType())).toString()));

        List<MaterialDocumentItemBO> itemBOS = itemGroupBy.entrySet().stream().map(entry -> {
            MaterialDocumentItemBO itemNew = entry.getValue().get(0);
            BigDecimal adjustQuantity = entry.getValue().stream().map(d -> d.getQuantityData().getAdjustQuantity()).reduce(BigDecimal::add).get();
            itemNew.getQuantityData().setAdjustQuantity(adjustQuantity);
            //合并的时候，价格是要重新计算的
            itemNew.getQuantityData().setPrice(materialDocCoreService.calBatchPrice(entry.getValue()));
            return itemNew;
        }).collect(Collectors.toList());

        materialDocumentIn.setMaterialDocumentItems(itemBOS);
    }

    private void transferItemBOs(MaterialDocumentBO materialDocument) {
        LogicalPlantBO logicalPlantDo = logicalPlantCoreService.getByLogicalPlantNo(materialDocument.getSupplyLogicalPlantNo());
        if (Objects.isNull(logicalPlantDo)) {
            throw new BusinessException(ResponseCodeEnum.LOGICAL_PLANT_NOT_EXIST, materialDocument.getSupplyLogicalPlantNo());
        }

        for (MaterialDocumentItemBO itemBO : materialDocument.getMaterialDocumentItems()) {
            itemBO.getWarehouseData().setAdjustType(materialDocument.getAdjustType());
            itemBO.getWarehouseData().setIo(materialDocument.getIo());
            itemBO.getWarehouseData()
                .setSupplyStockType(Optional.ofNullable(itemBO.getWarehouseData().getSupplyStockType()).orElse(StockTypeEnum.UNRESTRICTED.getCode()));
            itemBO.getWarehouseData().setSupplyLogicalPlantNo(logicalPlantDo.getLogicalPlantNo());
            itemBO.getWarehouseData().setSupplyLogicalPlantName(logicalPlantDo.getLogicalPlantName());

            if (StringUtils.isBlank(itemBO.getWarehouseData().getSupplyStorageLocationNo())) {
                List<StorageLocationBO> storageLocationPOS = storageLocationCoreService.listByPlantId(logicalPlantDo.getId());
                //不选指定为普通库位
                if(!CollectionUtils.isEmpty(storageLocationPOS)){
                    Optional<StorageLocationBO> storageLocationOp = storageLocationPOS.stream().filter(d -> StorageLocationTypeEnum.NORMAL.getCode().equals(d.getLocationType())).findFirst();
                    StorageLocationBO storageLocationDo = storageLocationOp.orElse(null);
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(Objects.nonNull(storageLocationDo) ? storageLocationDo.getStorageLocationNo() : EMPTY);
                    itemBO.getWarehouseData().setSupplyStorageLocationName(Objects.nonNull(storageLocationDo) ? storageLocationDo.getDescription() : EMPTY);
                }
            } else {
                StorageLocationBO storageLocationDo = storageLocationCoreService.getByStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo());
                itemBO.getWarehouseData()
                    .setSupplyStorageLocationName(Optional.ofNullable(storageLocationDo).isPresent() ? storageLocationDo.getDescription() : EMPTY);
            }

        }


        //不同逻辑仓和不同库存地点下的批次号不一样 logical-location
        Map<String, String> itemBatchNoBy = materialDocument.getMaterialDocumentItems().stream().collect(Collectors.groupingBy(
            d -> new StringJoiner(DEC).add(materialDocument.getSupplyLogicalPlantNo()).add(d.getWarehouseData().getSupplyStorageLocationNo()).toString()))
            .entrySet().stream().map(entry -> {
                EnumMapping enumMapping = new EnumMapping();
                String batchNo = materialDocCoreService
                    .getMaterialBatchNo(materialDocument.getAdjustType(), materialDocument.getSupplyLogicalPlantNo(), materialDocument.getOriginalNo());
                enumMapping.setKey(entry.getKey());
                enumMapping.setValue(batchNo);
                return enumMapping;
            }).collect(Collectors.toMap(EnumMapping::getKey, EnumMapping::getValue, (k1, k2) -> k2));

        for (MaterialDocumentItemBO itemBO : materialDocument.getMaterialDocumentItems()) {
            String batchNo = itemBatchNoBy.get(
                new StringJoiner(DEC).add(itemBO.getWarehouseData().getSupplyLogicalPlantNo()).add(itemBO.getWarehouseData().getSupplyStorageLocationNo())
                    .toString());

            itemBO.setBatchNo(batchNo);
            itemBO.getWarehouseData().setSupplyBatchNo(batchNo);
        }
    }
}
