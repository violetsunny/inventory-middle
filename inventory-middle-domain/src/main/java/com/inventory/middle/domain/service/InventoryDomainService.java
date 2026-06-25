package com.inventory.middle.domain.service;

import com.inventory.middle.client.dto.transit.CreateInTransitStockRequest;
import com.inventory.middle.client.dto.transit.TransferTransitStockRequest;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.inventory.QueryMaterialInventoryBO;
import java.util.List;

/** 库存业务操作服务接口 */
public interface InventoryDomainService {

    List<InventorySnapshotBO> queryInventoryData(QueryMaterialInventoryBO query);

    boolean checkTransferInStock(TransferTransitStockRequest request);

    boolean batchUpdateInTransitStock(List<InventorySupplyBO> supplyBOList);

    boolean batchCreateInTransitStock(List<CreateInTransitStockRequest> requestList);

    boolean transferInTransitStock(TransferTransitStockRequest request);
}
