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
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.QueryBatchNoReqBO;
import com.inventory.middle.domain.model.bo.inventory.QueryMaterialInventoryBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.InventoryCoreService;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import com.inventory.middle.domain.service.impl.LogicalPlantCoreService;
import com.inventory.middle.domain.service.impl.StorageLocationCoreService;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.client.dto.map.QueryInventoryMapDTO;
import com.inventory.middle.domain.service.external.RemoteInventoryMapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.DEC;
import static com.inventory.middle.domain.common.constants.CommonConstant.EMPTY;

/**
 * 入库数据组装  原有批次
 *
 * @author kll
 * @version $Id: MaterialDocBizHandler, v 0.1 2021/6/20 20:06 Exp $
 */
@Service
@Slf4j
public class MaterialBatchDataInOriginalHandle implements IHandler {

    @Resource
    private InventoryCoreService inventoryCoreService;
    @Resource
    private LogicalPlantCoreService logicalPlantCoreService;
    @Resource
    private StorageLocationCoreService storageLocationCoreService;
    @Resource
    private RemoteInventoryMapService remoteInventoryMapService;
    @Resource
    private MaterialDocBizConverter materialDocBizConverter;
    @Resource
    private MaterialDocCoreService materialDocCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "入库原数据组装")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocument = req.getMaterialDocument();
        //先进行数据转换是为了对原先的item进行默认赋值，方便后续的使用
        transferItemBOs(materialDocument);
        //生成入对象
        MaterialDocumentBO materialDocumentIn = materialDocBizConverter.copyMaterialDocumentIn(materialDocument);
        //入数据合并 sku-logical-locations-batch-stockType
        mergeItemBO(materialDocumentIn);
        //放入请求中
        req.setMaterialDocumentIn(materialDocumentIn);
        log.info("createMaterialDoc MaterialBatchDataInOriginalHandle 补充后的{},合并后的{}", JSON.toJSONString(materialDocument),JSON.toJSONString(materialDocumentIn));
        return message;
    }

    private void transferItemBOs(MaterialDocumentBO materialDocument) {
        //批次
        LogicalPlantBO logicalPlantDo = logicalPlantCoreService.getByLogicalPlantNo(materialDocument.getSupplyLogicalPlantNo());
        if (logicalPlantDo == null) {
            throw new BusinessException(ResponseCodeEnum.LOGICAL_PLANT_NOT_EXIST, materialDocument.getSupplyLogicalPlantNo());
        }
        for (MaterialDocumentItemBO itemBO : materialDocument.getMaterialDocumentItems()) {
            itemBO.getWarehouseData().setAdjustType(materialDocument.getAdjustType());
            itemBO.getWarehouseData().setIo(materialDocument.getIo());
            itemBO.getWarehouseData().setSupplyLogicalPlantNo(logicalPlantDo.getLogicalPlantNo());
            itemBO.getWarehouseData().setSupplyLogicalPlantName(logicalPlantDo.getLogicalPlantName());
            itemBO.getWarehouseData()
                .setSupplyStockType(Optional.ofNullable(itemBO.getWarehouseData().getSupplyStockType()).orElse(StockTypeEnum.UNRESTRICTED.getCode()));

            //挑选批次
            pickBatchNo(itemBO, logicalPlantDo);
            //补充地点
            supplementLocation(materialDocument, itemBO);
            //补充价格
            supplementPrice(materialDocument, itemBO);
        }
    }

    private void mergeItemBO(MaterialDocumentBO materialDocumentIn) {
        Map<String, List<MaterialDocumentItemBO>> itemGroupBy = materialDocumentIn.getMaterialDocumentItems().stream().collect(Collectors.groupingBy(
            d -> new StringJoiner(DEC)
                .add(d.getMaterialData().getMaterialCode()).add(d.getWarehouseData().getSupplyLogicalPlantNo())
                .add(d.getWarehouseData().getSupplyStorageLocationNo()).add(d.getWarehouseData().getSupplyBatchNo())
                .add(String.valueOf(d.getWarehouseData().getSupplyStockType())).toString()));

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

    private void supplementPrice(MaterialDocumentBO materialDocument, MaterialDocumentItemBO itemBO) {
        //盘盈的话用输入价格，不输入用移动平均价
        BigDecimal batchPrice = itemBO.getQuantityData().getPrice();
        if (Objects.isNull(batchPrice)) {
            QueryInventoryMapDTO query = new QueryInventoryMapDTO();
            query.setSkuCode(itemBO.getMaterialData().getMaterialCode());
            query.setLogicalPlantNo(materialDocument.getSupplyLogicalPlantNo());
            query.setTenantId(materialDocument.getTenantId());
            SingleResponse<InventoryMapDTO> result = remoteInventoryMapService.queryInventoryMap(query);
            //既然走这边就已经有过入的价格，会产生移动平均价
            batchPrice = Objects.nonNull(result) && Objects.nonNull(result.getData()) ? result.getData().getMap() : null;
            itemBO.getQuantityData().setPrice(batchPrice);
        }
    }

    private void supplementLocation(MaterialDocumentBO materialDocument, MaterialDocumentItemBO itemBO) {
        //如果选了批次没选库存地点，要反查
        if (StringUtils.isBlank(itemBO.getWarehouseData().getSupplyStorageLocationNo())) {
            QueryMaterialInventoryBO query = new QueryMaterialInventoryBO();
            query.setMaterialCode(itemBO.getMaterialData().getMaterialCode());
            query.setLogicalPlantNo(itemBO.getWarehouseData().getSupplyLogicalPlantNo());
            query.setBatchNo(itemBO.getWarehouseData().getSupplyBatchNo());
            query.setTenantId(materialDocument.getTenantId());
            List<InventorySnapshotBO> inventorySnapshots = inventoryCoreService.queryInventoryData(query);
            if (CollectionUtils.isEmpty(inventorySnapshots)) {
                log.warn("createMaterialDoc MaterialBatchNoInOriginalHandle 找不到该批次库存数据 {}", JSON.toJSONString(query));
                throw new BusinessException(ResponseCodeEnum.NO_INVENTORY, itemBO.getMaterialData().getMaterialCode());
            }
            InventorySnapshotBO inventorySnapshotBO = inventorySnapshots.stream().max(Comparator.comparing(d -> d.getBatchTime().getTime())).get();
            StorageLocationBO storageLocationDo = storageLocationCoreService.getById(inventorySnapshotBO.getLocationId());
            itemBO.getWarehouseData().setSupplyStorageLocationNo(storageLocationDo.getStorageLocationNo());
            itemBO.getWarehouseData()
                .setSupplyStorageLocationName(storageLocationDo.getDescription());
        }
    }

    private void pickBatchNo(MaterialDocumentItemBO itemBO, LogicalPlantBO logicalPlantDo) {
        String supplyBatchNo = itemBO.getWarehouseData().getSupplyBatchNo();
        if (StringUtils.isBlank(supplyBatchNo) && StringUtils.isNotBlank(itemBO.getWarehouseData().getSupplyLogicalPlantNo())) {
            QueryBatchNoReqBO queryBatchNoReqBO = new QueryBatchNoReqBO();
            queryBatchNoReqBO.setMaterialCode(itemBO.getMaterialData().getMaterialCode());
            itemBO.getWarehouseData().setSupplyLogicalPlantName(Optional.ofNullable(logicalPlantDo).isPresent() ? logicalPlantDo.getLogicalPlantName() : EMPTY);

            queryBatchNoReqBO.setLogicalPlantId(Optional.ofNullable(logicalPlantDo).isPresent() ? logicalPlantDo.getId() : null);
            StorageLocationBO storageLocationDo = storageLocationCoreService.getByStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo());
            queryBatchNoReqBO.setStorageLocationId(Optional.ofNullable(storageLocationDo).isPresent() ? storageLocationDo.getId() : null);
            itemBO.getWarehouseData()
                .setSupplyStorageLocationName(Optional.ofNullable(storageLocationDo).isPresent() ? storageLocationDo.getDescription() : EMPTY);

            List<InventorySnapshotBO> inventorySnapshotBOS = inventoryCoreService.queryInventoryBatchNo(queryBatchNoReqBO);
            //批次不能为空
            //物料code必须存在
            if (CollectionUtils.isEmpty(inventorySnapshotBOS)) {
                log.warn("createMaterialDoc MaterialBatchNoInOriginalHandle 没有库存数据 {}", JSON.toJSONString(queryBatchNoReqBO));
                throw new BusinessException(ResponseCodeEnum.NO_INVENTORY, itemBO.getMaterialData().getMaterialCode());
            }
            //入最晚的批次
            InventorySnapshotBO inventorySnapshotBO = inventorySnapshotBOS.stream().max(Comparator.comparing(d -> d.getBatchTime().getTime())).get();
            itemBO.getWarehouseData().setSupplyBatchNo(inventorySnapshotBO.getBatchNo());
        }
    }

}
