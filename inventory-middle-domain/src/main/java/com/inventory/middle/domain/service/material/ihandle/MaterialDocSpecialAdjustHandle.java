/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.model.enums.StorageLocationTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 需要特殊库位的移动类型处理逻辑
 *
 * @author kanglele
 * @version $Id: MaterialDocSpecialAdjustHandle, v 0.1 2021/10/20 19:38 Exp $
 */
@Service
@Slf4j
public class MaterialDocSpecialAdjustHandle implements IHandler {

    @Resource
    private LogicalPlantCoreService logicalPlantCoreService;
    @Resource
    private StorageLocationCoreService storageLocationCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "特殊库位的移动类型处理")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocument = req.getMaterialDocument();
        //对于特殊库位转化
        transferMaterialData(materialDocument);
        return message;
    }

    private void transferMaterialData(MaterialDocumentBO materialDocument) {
        LogicalPlantBO demandLogicalPlant = logicalPlantCoreService.getByLogicalPlantNo(materialDocument.getDemandLogicalPlantNo());
        List<StorageLocationBO> demandStorageLocationList = Lists.newArrayList();
        if (Objects.nonNull(demandLogicalPlant)) {
            demandStorageLocationList = storageLocationCoreService.listByPlantId(demandLogicalPlant.getId());
        }

        LogicalPlantBO supplyLogicalPlant = logicalPlantCoreService.getByLogicalPlantNo(materialDocument.getSupplyLogicalPlantNo());
        List<StorageLocationBO> supplyStorageLocationList = Lists.newArrayList();
        if (Objects.nonNull(supplyLogicalPlant)) {
            supplyStorageLocationList = storageLocationCoreService.listByPlantId(supplyLogicalPlant.getId());
        }

        for (MaterialDocumentItemBO itemBO : materialDocument.getMaterialDocumentItems()) {
            //普通库和特殊库转移默认赋值
            switch (Objects.requireNonNull(MaterialAdjustTypeEnum.tansfer(materialDocument.getAdjustType()))) {
                case OWW411:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.NORMAL,
                            demandStorageLocationList));
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGN_MANUFACTURE,
                            supplyStorageLocationList));
                    break;
                case OWW413:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGN_MANUFACTURE,
                            demandStorageLocationList));
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.NORMAL,
                            supplyStorageLocationList));
                    break;
                case OKJ411:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.NORMAL,
                            demandStorageLocationList));
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGNMENT,
                            supplyStorageLocationList));
                    break;
                case OKJ413:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGNMENT,
                            demandStorageLocationList));
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.NORMAL,
                            supplyStorageLocationList));
                    break;
                case OGJ411:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGNMENT_SUPPLIER,
                            demandStorageLocationList));
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.NORMAL,
                            supplyStorageLocationList));
                    break;
                case OGJ413:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.NORMAL,
                            demandStorageLocationList));
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGNMENT_SUPPLIER,
                            supplyStorageLocationList));
                    break;
                case VJ101:
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGNMENT_SUPPLIER,
                            supplyStorageLocationList));
                    break;
                case RWW101:
                    itemBO.getWarehouseData().setSupplyStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getSupplyStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGN_MANUFACTURE,
                            supplyStorageLocationList));
                    break;
                case RWW201:
                    itemBO.getWarehouseData().setDemandStorageLocationNo(
                        setStorageLocationNo(itemBO.getWarehouseData().getDemandStorageLocationNo(), StorageLocationTypeEnum.SPECIAL_CONSIGN_MANUFACTURE,
                            demandStorageLocationList));
                    break;
                default:
                    break;
            }
        }

    }

    private String setStorageLocationNo(String storageLocationNo, StorageLocationTypeEnum storageLocationTypeEnum,
        List<StorageLocationBO> storageLocationList) {
        if (StringUtils.isBlank(storageLocationNo)) {
            Optional<StorageLocationBO> storageLocationOp =
                storageLocationList.stream().filter(d -> storageLocationTypeEnum.getCode().equals(d.getLocationType())).findFirst();
            if (!storageLocationOp.isPresent()) {
                throw new BusinessException(ResponseCodeEnum.SPECIAL_STORAGE_LOCATION_EXIST, storageLocationTypeEnum.getDesc());
            }
            storageLocationNo = storageLocationOp.get().getStorageLocationNo();
        }

        return storageLocationNo;
    }
}
