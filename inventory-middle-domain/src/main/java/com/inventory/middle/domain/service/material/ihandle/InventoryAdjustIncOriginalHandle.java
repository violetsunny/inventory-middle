/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.client.enums.InventorySupplyTypeEnum;
import com.inventory.middle.domain.model.enums.DeletedEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.inventory.QueryMaterialInventoryBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.InventoryCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.ZERO;

/**
 * 原库存调增
 *
 * @author kll
 * @version $Id: AdjustInventoryIncHandle, v 0.1 2021/6/21 15:46 Exp $
 */
@Service
@Slf4j
public class InventoryAdjustIncOriginalHandle implements IHandler {

    @Resource
    private InventoryCoreService inventoryCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "原库存调增")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentIn = req.getMaterialDocumentIn();
        //库存调增 更新 用原有的批次信息入库
        List<InventorySupplyBO> inventorySupplyBOS =
            materialDocumentIn.getMaterialDocumentItems().stream().map(d -> transferInventoryBO(materialDocumentIn, d)).collect(Collectors.toList());
        req.setInventorySupplys(inventorySupplyBOS);
        log.info("createMaterialDoc InventoryAdjustIncOriginalHandle 库存调增 {}", JSON.toJSONString(inventorySupplyBOS));
        return message;
    }

    private InventorySupplyBO transferInventoryBO(MaterialDocumentBO materialDocumentIn, MaterialDocumentItemBO materialDocumentItemBO) {
        QueryMaterialInventoryBO queryMaterialInventoryBO = conversionQueryMaterialInventoryBO(materialDocumentIn, materialDocumentItemBO);
        List<InventorySupplyBO> inventorySupplyBOS = inventoryCoreService.queryInventorySupplyData(queryMaterialInventoryBO);
        if (CollectionUtils.isEmpty(inventorySupplyBOS)) {
            throw new BusinessException(ResponseCodeEnum.MATERIAL_BATCH, materialDocumentItemBO.getMaterialData().getMaterialCode());
        }
        InventorySupplyBO inventorySupplyBO = inventorySupplyBOS.get(0);
        inventorySupplyBO.setId(null);
        inventorySupplyBO.setMaterialDocId(materialDocumentIn.getMaterialDocId());
        inventorySupplyBO.setStockType(Optional.ofNullable(materialDocumentItemBO.getWarehouseData().getSupplyStockType())
                .orElse(StockTypeEnum.UNRESTRICTED.getCode()));
        inventorySupplyBO.setUnrestricted(BigDecimal.ZERO);
        inventorySupplyBO.setDamaged(BigDecimal.ZERO);
        inventorySupplyBO.setInspection(BigDecimal.ZERO);
        if (StockTypeEnum.UNRESTRICTED.getCode().equals(inventorySupplyBO.getStockType())) {
            inventorySupplyBO.setUnrestricted(materialDocumentItemBO.getQuantityData().getAdjustQuantity());
        }
        if (StockTypeEnum.DAMAGED.getCode().equals(inventorySupplyBO.getStockType())) {
            inventorySupplyBO.setDamaged(materialDocumentItemBO.getQuantityData().getAdjustQuantity());
        }
        if (StockTypeEnum.INSPECTION.getCode().equals(inventorySupplyBO.getStockType())) {
            inventorySupplyBO.setInspection(materialDocumentItemBO.getQuantityData().getAdjustQuantity());
        }

        inventorySupplyBO.setBatchPrice(Optional.ofNullable(materialDocumentItemBO.getQuantityData().getPrice())
            .orElse(inventorySupplyBO.getBatchPrice()));
        inventorySupplyBO.setTenantId(Optional.ofNullable(materialDocumentIn.getTenantId()).orElse(ZERO));
        inventorySupplyBO.setCreateTime(LocalDateTime.now());
        inventorySupplyBO.setCreatorId(Optional.ofNullable(materialDocumentIn.getOperator()).orElse(ZERO));
        inventorySupplyBO.setUpdatorId(Optional.ofNullable(materialDocumentIn.getOperator()).orElse(ZERO));
        inventorySupplyBO.setUpdateTime(LocalDateTime.now());
        inventorySupplyBO.setDeleted(DeletedEnum.FALSE.getCode());
        return inventorySupplyBO;
    }

    private QueryMaterialInventoryBO conversionQueryMaterialInventoryBO(MaterialDocumentBO materialDocument, MaterialDocumentItemBO itemBO) {
        QueryMaterialInventoryBO queryMaterialInventoryBO = new QueryMaterialInventoryBO();
        queryMaterialInventoryBO.setMaterialCode(itemBO.getMaterialData().getMaterialCode());
        //已有批次
        queryMaterialInventoryBO.setBatchNo(itemBO.getWarehouseData().getSupplyBatchNo());
        queryMaterialInventoryBO.setTenantId(materialDocument.getTenantId());
        queryMaterialInventoryBO.setSupplyType(InventorySupplyTypeEnum.IN_STOCK.getCode());
        return queryMaterialInventoryBO;
    }
}
