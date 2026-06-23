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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.INC;

/**
 * 入库凭证
 *
 * @author kll
 * @version $Id: MaterialDocHandle, v 0.1 2021/6/21 16:01 Exp $
 */
@Service
@Slf4j
public class MaterialDocInHandle implements IHandler {

    @Resource
    private MaterialDocCoreService materialDocCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "入库凭证")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentIn = req.getMaterialDocumentIn();
        //生成物料凭证
        //生成新的入item
        List<MaterialDocumentItemBO> itemBOList = conversionItemBOs(materialDocumentIn);
        materialDocumentIn.setMaterialDocumentItems(itemBOList);
        //是否计算MAP
        materialDocCoreService.initMap(materialDocumentIn);
        log.info("createMaterialDoc MaterialDocInHandle 入库凭证 {}", JSON.toJSONString(materialDocumentIn));
        return message;
    }

    private List<MaterialDocumentItemBO> conversionItemBOs(MaterialDocumentBO materialDocument) {
        return materialDocument.getMaterialDocumentItems().stream().map(this::converterItemBO).collect(Collectors.toList());
    }

    private MaterialDocumentItemBO converterItemBO(MaterialDocumentItemBO itemBO) {
        MaterialDocumentItemBO materialDocumentItemBO = new MaterialDocumentItemBO();
        materialDocumentItemBO.setBatchNo(Optional.ofNullable(itemBO.getBatchNo()).filter(StringUtils::isNotBlank).isPresent() ? itemBO.getBatchNo() :
            itemBO.getWarehouseData().getSupplyBatchNo());
        materialDocumentItemBO.setMaterialCode(itemBO.getMaterialData().getMaterialCode());
        materialDocumentItemBO.setItemCategory(MaterialDocCategoryEnum.IN.getCode());

        materialDocumentItemBO.setMaterialData(itemBO.getMaterialData());
        materialDocumentItemBO.setQuantityData(itemBO.getQuantityData());

        WarehouseDataBO warehouseDataBO = new WarehouseDataBO();
        warehouseDataBO.setIo(INC);
        warehouseDataBO.setSupply(itemBO.getWarehouseData().getSupply());
        warehouseDataBO.setSupplySaleOrderNo(itemBO.getWarehouseData().getSupplySaleOrderNo());
        warehouseDataBO.setSupplySaleOrderItemNo(itemBO.getWarehouseData().getSupplySaleOrderItemNo());
        warehouseDataBO.setSupplyLogicalPlantNo(itemBO.getWarehouseData().getSupplyLogicalPlantNo());
        warehouseDataBO.setSupplyLogicalPlantName(itemBO.getWarehouseData().getSupplyLogicalPlantName());
        warehouseDataBO.setSupplyStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo());
        warehouseDataBO.setSupplyStorageLocationName(itemBO.getWarehouseData().getSupplyStorageLocationName());
        warehouseDataBO.setSupplyBatchNo(itemBO.getWarehouseData().getSupplyBatchNo());
        warehouseDataBO.setSupplyStockType(itemBO.getWarehouseData().getSupplyStockType());
        warehouseDataBO.setAdjustType(itemBO.getWarehouseData().getAdjustType());
        warehouseDataBO.setAdjustReason(itemBO.getWarehouseData().getAdjustReason());
        materialDocumentItemBO.setWarehouseData(warehouseDataBO);

        materialDocumentItemBO.setMaterialExtData(itemBO.getMaterialExtData());
        materialDocumentItemBO.setFinanceData(itemBO.getFinanceData());
        materialDocumentItemBO.setMapJournalData(itemBO.getMapJournalData());
        return materialDocumentItemBO;
    }
}
