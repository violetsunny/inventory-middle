package com.inventory.middle.domain.service.external;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.enums.StockTypeEnum;

import java.util.List;

/** 出库流程服务接口（domain 层定义） */
public interface MaterialDocOutboundService {

    void doOutbound(MaterialDocumentBO materialDocumentBO);

    void doOutbound(MaterialDocumentBO materialDocumentBO, Boolean outOnly);

    void checkAdjustNumber(MaterialDocumentBO materialDocumentBO, Boolean needBatchNo);

    void doBiz(String operator, MaterialDocumentItemBO itemBO,
               List<InventorySnapshotBO> inventorySnapshotBOs,
               StockTypeEnum stockTypeEnum, Long newMaterialDocId,
               List<MaterialDocumentItemBO> list, Boolean needBatchNo);
}
