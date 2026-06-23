package com.inventory.middle.domain.service.outbound;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author holmes
 * @Classname MaterialDocOutboundOzp721HandlerService
 * @Description 赠品退货
 * @Date 2021/6/23 19:48
 */
@Slf4j
@Service
public class MaterialDocOutboundOzp721HandlerService extends MaterialDocOutboundDefaultHandlerService {


    @Override
    public void doOutbound(MaterialDocumentBO materialDocumentBO) {
        doOutbound(materialDocumentBO, false);
    }

    @Override
    public void doOutbound(MaterialDocumentBO materialDocumentBO, Boolean outOnly) {
        //check
        checkAdjustNumber(materialDocumentBO, false);
        //move
        generateDemandData(materialDocumentBO, outOnly);

    }

    @Override
    public BigDecimal countAvailable(MaterialDocumentBO materialDocumentBO, StockTypeEnum stockTypeEnum, MaterialDocumentItemBO materialDocumentItemBO, boolean needBatchNo) {
        String batchNo = null;
        String storageLocationNo = null;
        if (needBatchNo) {
            batchNo = materialDocumentItemBO.getWarehouseData().getDemandBatchNo();
            storageLocationNo = materialDocumentItemBO.getWarehouseData().getDemandStorageLocationNo();
        }
        return inventorySnapshotCoreService.countAvailable(materialDocumentItemBO.getMaterialData().getMaterialCode(), materialDocumentBO.getDemandLogicalPlantNo(), stockTypeEnum,
                materialDocumentBO.getTenantId(), batchNo, storageLocationNo);
    }



}
