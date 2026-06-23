/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.model;

import com.inventory.middle.client.dto.transit.TransferTransitStockRequest;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kanglele
 * @version $Id: MaterialDocInReq, v 0.1 2021/8/20 10:31 Exp $
 */
@Data
public class MaterialDocInvReq implements Serializable {
    
    private MaterialDocumentBO materialDocument;

    private MaterialDocumentBO materialDocumentIn;

    private MaterialDocumentBO materialDocumentOut;

    private List<InventorySupplyBO> inventorySupplys;

    private TransferTransitStockRequest transitStockRequest;

    private List<CreateMaterialLogicalPlantRefBO> materialLogicalPlantRefs;
}
