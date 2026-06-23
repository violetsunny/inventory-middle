/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.service.InventoryDomainService;
import com.inventory.middle.client.dto.transit.TransferTransitMaterialDTO;
import com.inventory.middle.client.dto.transit.TransferTransitStockRequest;
import com.inventory.middle.client.enums.InventorySupplyTypeEnum;
import com.inventory.middle.client.enums.TransferTransitTypeEnum;
import com.inventory.middle.domain.model.enums.DeletedEnum;
import com.inventory.middle.domain.model.enums.MaterialDocRefOrderTypeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.common.util.DateUtils;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.impl.LogicalPlantCoreService;
import com.inventory.middle.domain.service.impl.StorageLocationCoreService;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.EMPTY;
import static com.inventory.middle.domain.common.constants.CommonConstant.ZERO;

/**
 * 库存调增
 *
 * @author kll
 * @version $Id: AdjustInventoryIncHandle, v 0.1 2021/6/21 15:46 Exp $
 */
@Service
@Slf4j
public class InventoryAdjustIncHandle implements IHandler {

    @Resource
    private LogicalPlantCoreService logicalPlantCoreService;
    @Resource
    private StorageLocationCoreService storageLocationCoreService;
    @Resource
    private InventoryDomainService inventoryDomainService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "库存调增")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentIn = req.getMaterialDocumentIn();
        MaterialDocumentBO materialDocument = req.getMaterialDocument();
        //库存调增
        List<InventorySupplyBO> inventorySupplyBOS = transferInventorySupplyBOs(materialDocumentIn);
        req.setInventorySupplys(inventorySupplyBOS);
        log.info("createMaterialDoc InventoryAdjustIncHandle 库存调增 {}", JSON.toJSONString(inventorySupplyBOS));
        //调在途
        TransferTransitStockRequest request = transferTransitStockRequest(materialDocumentIn, materialDocument, inventorySupplyBOS);
        req.setTransitStockRequest(request);
        log.info("createMaterialDoc InventoryAdjustIncHandle 在途库存调整 {}", JSON.toJSONString(request));
        //建立物料和逻辑仓关系
        List<CreateMaterialLogicalPlantRefBO> materialLogicalPlantRefs = transferMaterialLogicalPlantRef(inventorySupplyBOS);
        req.setMaterialLogicalPlantRefs(materialLogicalPlantRefs);
        log.info("createMaterialDoc InventoryAdjustIncHandle 建立物料和逻辑仓关系 {}", JSON.toJSONString(materialLogicalPlantRefs));
        return message;
    }

    private List<CreateMaterialLogicalPlantRefBO> transferMaterialLogicalPlantRef(List<InventorySupplyBO> inventorySupplyBOS) {
        if (CollectionUtils.isEmpty(inventorySupplyBOS)) {
            return Lists.newArrayList();
        }
        return inventorySupplyBOS.stream().map(data -> {
            CreateMaterialLogicalPlantRefBO materialLogicalPlantRefBO = new CreateMaterialLogicalPlantRefBO();
            materialLogicalPlantRefBO.setMaterialCode(data.getMaterialCode());
            materialLogicalPlantRefBO.setLogicalPlantId(data.getLogicalPlantId());
            materialLogicalPlantRefBO.setLogicalPlantNo(data.getLogicalPlantNo());
            materialLogicalPlantRefBO.setOperatorId(data.getCreatorId());
            materialLogicalPlantRefBO.setTenantId(data.getTenantId());
            return materialLogicalPlantRefBO;
        }).collect(Collectors.toList());
    }

    private TransferTransitStockRequest transferTransitStockRequest(MaterialDocumentBO materialDocumentIn, MaterialDocumentBO materialDocument,
        List<InventorySupplyBO> inventorySupplyBOS) {
        if ((StringUtils.isBlank(materialDocument.getOriginalNo()) && StringUtils.isBlank(materialDocument.getDeliverNo()))
            || MaterialDocRefOrderTypeEnum.MATERIAL_DOC.getCode().equals(materialDocument.getOriginalNoType())) {
            return null;
        }

        TransferTransitStockRequest request = new TransferTransitStockRequest();
        request.setTenantId(materialDocumentIn.getTenantId());
        request.setOperatorId(materialDocumentIn.getOperator());
        request.setSourceOrderNo(materialDocument.getOriginalNo());
        request.setSourceOrderType(materialDocument.getOriginalNoType());
        request.setDeliveryNo(materialDocument.getDeliverNo());

        List<TransferTransitMaterialDTO> materialDTOList = Lists.newArrayList();
        for (InventorySupplyBO inventorySupplyBO : inventorySupplyBOS) {
            TransferTransitMaterialDTO transitMaterialDTO = new TransferTransitMaterialDTO();
            transitMaterialDTO.setLogicalPlantId(inventorySupplyBO.getLogicalPlantId());
            transitMaterialDTO.setLocationId(inventorySupplyBO.getLocationId());
            transitMaterialDTO.setMaterialCode(inventorySupplyBO.getMaterialCode());
            if (StockTypeEnum.UNRESTRICTED.getCode().equals(inventorySupplyBO.getStockType())) {
                transitMaterialDTO.setInBoundQuantity(inventorySupplyBO.getUnrestricted());
            }
            if (StockTypeEnum.DAMAGED.getCode().equals(inventorySupplyBO.getStockType())) {
                transitMaterialDTO.setInBoundQuantity(inventorySupplyBO.getDamaged());
            }
            if (StockTypeEnum.INSPECTION.getCode().equals(inventorySupplyBO.getStockType())) {
                transitMaterialDTO.setInBoundQuantity(inventorySupplyBO.getInspection());
            }
            transitMaterialDTO.setStockType(inventorySupplyBO.getStockType());
            transitMaterialDTO.setTransferType(TransferTransitTypeEnum.IN.getCode());
            materialDTOList.add(transitMaterialDTO);
        }
        request.setMaterialDTOList(materialDTOList);

        //check是否能调整在途
        if (!inventoryDomainService.checkTransferInStock(request)) {
            log.info("createMaterialDoc InventoryAdjustIncHandle 无需调整在途:{}", JSON.toJSONString(request));
            return null;
        }
        return request;
    }

    private List<InventorySupplyBO> transferInventorySupplyBOs(MaterialDocumentBO materialDocumentIn) {
        LogicalPlantBO logicalPlantBO = logicalPlantCoreService.getByLogicalPlantNo(materialDocumentIn.getSupplyLogicalPlantNo());
        return materialDocumentIn.getMaterialDocumentItems().stream().map(d -> converterInventorySupplyBO(materialDocumentIn, d, logicalPlantBO))
            .collect(Collectors.toList());
    }

    private InventorySupplyBO converterInventorySupplyBO(MaterialDocumentBO materialDocumentIn, MaterialDocumentItemBO materialDocumentItemBO,
        LogicalPlantBO logicalPlantBO) {
        InventorySupplyBO inventorySupplyBO = new InventorySupplyBO();
        inventorySupplyBO.setMaterialDocId(materialDocumentIn.getMaterialDocId());
        inventorySupplyBO.setMaterialCode(materialDocumentItemBO.getMaterialData().getMaterialCode());
        inventorySupplyBO.setMaterialCategoryCode(StringUtils.isNotBlank(materialDocumentItemBO.getMaterialData().getMaterialCategoryCode())
                ?materialDocumentItemBO.getMaterialData().getMaterialCategoryCode():EMPTY);
        inventorySupplyBO.setBatchNo(materialDocumentItemBO.getBatchNo());
        inventorySupplyBO.setBatchTime(new Date());
        inventorySupplyBO.setOwnerId(0L);

        inventorySupplyBO.setLogicalPlantId(Optional.ofNullable(logicalPlantBO).isPresent() ? logicalPlantBO.getId() : 0L);
        inventorySupplyBO.setLogicalPlantNo(Optional.ofNullable(logicalPlantBO).isPresent() ? logicalPlantBO.getLogicalPlantNo() : EMPTY);
        inventorySupplyBO.setLogicalPlantType(Optional.ofNullable(logicalPlantBO).isPresent() ? logicalPlantBO.getType() : 0);
        Long WarehouseId = Optional.ofNullable(logicalPlantBO).isPresent() ? logicalPlantBO.getWarehouseId() : null;
        inventorySupplyBO.setWarehouseId(WarehouseId);

        if (StringUtils.isBlank(materialDocumentItemBO.getWarehouseData().getSupplyStorageLocationNo())) {
            List<StorageLocationBO> storageLocationPOS = storageLocationCoreService.listByPlantId(logicalPlantBO.getId());
            inventorySupplyBO.setLocationId(
                Optional.ofNullable(storageLocationPOS).filter(d -> !CollectionUtils.isEmpty(d)).isPresent() ? storageLocationPOS.get(0).getId() : 0L);
            inventorySupplyBO.setLocationNo(Optional.ofNullable(storageLocationPOS).filter(d -> !CollectionUtils.isEmpty(d)).isPresent() ?
                storageLocationPOS.get(0).getStorageLocationNo() : EMPTY);
        } else {
            StorageLocationBO storageLocationDo =
                storageLocationCoreService.getByStorageLocationNo(materialDocumentItemBO.getWarehouseData().getSupplyStorageLocationNo());
            inventorySupplyBO.setLocationId(Optional.ofNullable(storageLocationDo).isPresent() ? storageLocationDo.getId() : 0L);
            inventorySupplyBO.setLocationNo(Optional.ofNullable(storageLocationDo).isPresent() ? storageLocationDo.getStorageLocationNo() : EMPTY);
        }

        inventorySupplyBO.setUnrestricted(BigDecimal.ZERO);
        inventorySupplyBO.setDamaged(BigDecimal.ZERO);
        inventorySupplyBO.setInspection(BigDecimal.ZERO);
        Integer stockType = Optional.ofNullable(materialDocumentItemBO.getWarehouseData().getSupplyStockType()).orElse(StockTypeEnum.UNRESTRICTED.getCode());
        inventorySupplyBO.setStockType(stockType);
        if (StockTypeEnum.UNRESTRICTED.getCode().equals(stockType)) {
            inventorySupplyBO.setUnrestricted(materialDocumentItemBO.getQuantityData().getAdjustQuantity());
        }
        if (StockTypeEnum.DAMAGED.getCode().equals(stockType)) {
            inventorySupplyBO.setDamaged(materialDocumentItemBO.getQuantityData().getAdjustQuantity());
        }
        if (StockTypeEnum.INSPECTION.getCode().equals(stockType)) {
            inventorySupplyBO.setInspection(materialDocumentItemBO.getQuantityData().getAdjustQuantity());
        }

        inventorySupplyBO.setSupplyType(InventorySupplyTypeEnum.IN_STOCK.getCode());

        if (Objects.nonNull(materialDocumentItemBO.getMaterialExtData())) {
            if (StringUtils.isNotBlank(materialDocumentItemBO.getMaterialExtData().getBatchProduceDate())) {
                inventorySupplyBO.setEta(DateUtils.stringToDateCommon(materialDocumentItemBO.getMaterialExtData().getBatchProduceDate()));
            }
            if (StringUtils.isNotBlank(materialDocumentItemBO.getMaterialExtData().getBatchProduceDate()) && Objects
                .nonNull(materialDocumentItemBO.getMaterialExtData().getValidDays())) {
                inventorySupplyBO.setDueDate(DateUtils.getBeforeOrAfterDateByDays(DateUtils.asLocalDateTime(inventorySupplyBO.getEta()),
                    materialDocumentItemBO.getMaterialExtData().getValidDays()));
            }
        }

        inventorySupplyBO.setUom(materialDocumentItemBO.getQuantityData().getUom());
        inventorySupplyBO.setCurrency(materialDocumentItemBO.getQuantityData().getCurrency());
        inventorySupplyBO.setTenantId(Optional.ofNullable(materialDocumentIn.getTenantId()).orElse(ZERO));
        inventorySupplyBO.setBatchPrice(Optional.ofNullable(materialDocumentItemBO.getQuantityData().getPrice()).orElse(BigDecimal.ZERO));
        inventorySupplyBO.setDeleted(DeletedEnum.FALSE.getCode());
        inventorySupplyBO.setCreateTime(LocalDateTime.now());
        inventorySupplyBO.setCreatorId(Optional.ofNullable(materialDocumentIn.getOperator()).orElse(ZERO));
        inventorySupplyBO.setUpdateTime(LocalDateTime.now());
        inventorySupplyBO.setUpdatorId(Optional.ofNullable(materialDocumentIn.getOperator()).orElse(ZERO));
        return inventorySupplyBO;
    }
}
