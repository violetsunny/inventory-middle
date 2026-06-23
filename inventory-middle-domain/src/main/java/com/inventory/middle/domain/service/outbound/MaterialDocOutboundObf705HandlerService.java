package com.inventory.middle.domain.service.outbound;

import org.apache.commons.lang3.StringUtils;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
// dal entity removed: InventorySnapshotBO;
import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.client.dto.map.QueryInventoryMapDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author holmes
 * @Classname MaterialDocOutboundObf705HandlerService
 * @Description 报废出库
 * @Date 2021/8/27 19:49
 */
@Service
@Slf4j
public class MaterialDocOutboundObf705HandlerService extends MaterialDocOutboundDefaultHandlerService {
    @Override
    public void doOutbound(MaterialDocumentBO materialDocumentBO) {
        //check
        checkAdjustNumber(materialDocumentBO, false);
        //move
        generateDemandData(materialDocumentBO, false);
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



    @Override
    protected BigDecimal findBatchPrice(MaterialDocumentItemBO target, InventorySnapshotBO snp) {
        if (target.getQuantityData().getPrice() != null) {
            return target.getQuantityData().getPrice();
        }
        QueryInventoryMapDTO query = new QueryInventoryMapDTO();
        query.setTenantId(snp.getTenantId());
        query.setSkuCode(snp.getMaterialCode());
        query.setLogicalPlantNo(snp.getLogicalPlantNo());
        SingleResponse<InventoryMapDTO> result = remoteInventoryMapService.queryInventoryMap(query);

        return Objects.nonNull(result) && Objects.nonNull(result.getData()) ? result.getData().getMap() : snp.getBatchPrice();
    }

}
