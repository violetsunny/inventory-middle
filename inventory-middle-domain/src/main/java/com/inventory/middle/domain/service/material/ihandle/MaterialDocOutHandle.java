/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.model.enums.MaterialDocCategoryEnum;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.material.WarehouseDataBO;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import com.inventory.middle.domain.service.impl.StorageLocationCoreService;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.DEC;

/**
 * 出库凭证
 *
 * @author kll
 * @version $Id: MaterialDocHandle, v 0.1 2021/6/21 16:01 Exp $
 */
@Service
@Slf4j
public class MaterialDocOutHandle implements IHandler {

    @Resource
    private StorageLocationCoreService storageLocationCoreService;
    @Resource
    private MaterialDocCoreService materialDocCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "出库凭证")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentOut = req.getMaterialDocumentOut();
        //取出的item
        List<MaterialDocumentItemBO> itemBOS = materialDocumentOut.getMaterialDocumentItems();
        //生成物料凭证
        //生成新的出item
        List<MaterialDocumentItemBO> itemBOList = conversionOutItemBOs(itemBOS);
        materialDocumentOut.setMaterialDocumentItems(itemBOList);
        //是否计算MAP
        materialDocCoreService.initMap(materialDocumentOut);
        log.info("createMaterialDoc MaterialDocOutHandle 出库凭证 {}", JSON.toJSONString(materialDocumentOut));
        return message;
    }

    private List<MaterialDocumentItemBO> conversionOutItemBOs(List<MaterialDocumentItemBO> itemBOS) {
        //出的item
        if (CollectionUtils.isEmpty(itemBOS)) {
            return Lists.newArrayList();
        }
        return itemBOS.stream().map(d -> converterItemBO(d)).collect(Collectors.toList());
    }

    private MaterialDocumentItemBO converterItemBO(MaterialDocumentItemBO itemBO) {
        MaterialDocumentItemBO materialDocumentItemBO = new MaterialDocumentItemBO();
        materialDocumentItemBO.setBatchNo(Optional.ofNullable(itemBO.getBatchNo()).filter(StringUtils::isNotBlank).isPresent() ? itemBO.getBatchNo() :
            itemBO.getWarehouseData().getDemandBatchNo());
        materialDocumentItemBO.setMaterialCode(itemBO.getMaterialData().getMaterialCode());
        materialDocumentItemBO.setItemCategory(MaterialDocCategoryEnum.OUT.getCode());

        materialDocumentItemBO.setMaterialData(itemBO.getMaterialData());
        materialDocumentItemBO.setQuantityData(itemBO.getQuantityData());

        WarehouseDataBO warehouseDataBO = new WarehouseDataBO();
        warehouseDataBO.setIo(DEC);
        warehouseDataBO.setDemand(itemBO.getWarehouseData().getDemand());
        warehouseDataBO.setDemandSaleOrderNo(itemBO.getWarehouseData().getDemandSaleOrderNo());
        warehouseDataBO.setDemandSaleOrderItemNo(itemBO.getWarehouseData().getDemandSaleOrderItemNo());
        warehouseDataBO.setDemandLogicalPlantNo(itemBO.getWarehouseData().getDemandLogicalPlantNo());
        warehouseDataBO.setDemandLogicalPlantName(itemBO.getWarehouseData().getDemandLogicalPlantName());
        warehouseDataBO.setDemandStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo());
        StorageLocationBO storageLocationDo = storageLocationCoreService.getByStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo());
        warehouseDataBO.setDemandStorageLocationName(Optional.ofNullable(storageLocationDo).isPresent() ? storageLocationDo.getDescription() : "");
        warehouseDataBO.setDemandBatchNo(itemBO.getWarehouseData().getDemandBatchNo());
        warehouseDataBO.setDemandStockType(itemBO.getWarehouseData().getDemandStockType());
        warehouseDataBO.setAdjustType(itemBO.getWarehouseData().getAdjustType());
        warehouseDataBO.setAdjustReason(itemBO.getWarehouseData().getAdjustReason());
        materialDocumentItemBO.setWarehouseData(warehouseDataBO);

        materialDocumentItemBO.setMaterialExtData(itemBO.getMaterialExtData());
        materialDocumentItemBO.setFinanceData(itemBO.getFinanceData());
        materialDocumentItemBO.setMapJournalData(itemBO.getMapJournalData());
        return materialDocumentItemBO;
    }
}
