package com.inventory.middle.domain.service.outbound;

import org.apache.commons.lang3.StringUtils;
import com.inventory.middle.domain.service.outbound.MaterialDocOutboundDefaultHandlerService;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
// dal entity removed: InventorySnapshotBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author holmes
 * @Classname MaterialDocOutboundOly201HandlerService
 * @Description 领料出库
 * @Date 2021/6/23 19:48
 */
@Slf4j
@Service
public class MaterialDocOutboundOly201HandlerService extends MaterialDocOutboundDefaultHandlerService {


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
