package com.inventory.middle.domain.service.impl;

import com.inventory.middle.client.dto.transit.CreateInTransitStockRequest;
import com.inventory.middle.client.dto.transit.TransferTransitStockRequest;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.inventory.QueryMaterialInventoryBO;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.domain.repository.InventorySupplyRepository;
import com.inventory.middle.domain.service.InventoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存操作服务实现
 * 迁移自: com.enn.inventory.center.core.service.impl.InventoryBizServiceImpl
 */
@Slf4j
@Service
public class InventoryDomainServiceImpl implements InventoryDomainService {

    @Resource
    private InventorySnapshotRepository inventorySnapshotRepository;

    @Resource
    private InventorySupplyRepository inventorySupplyRepository;

    @Override
    public List<InventorySnapshotBO> queryInventoryData(QueryMaterialInventoryBO query) {
        Map<String, Object> params = new HashMap<>();
        if (query != null) {
            if (query.getMaterialCode() != null) {
                params.put("materialCode", query.getMaterialCode());
            }
            if (query.getLogicalPlantNo() != null) {
                params.put("logicalPlantNo", query.getLogicalPlantNo());
            }
            if (query.getBatchNo() != null) {
                params.put("batchNo", query.getBatchNo());
            }
            if (query.getTenantId() != null) {
                params.put("tenantId", query.getTenantId());
            }
        }
        List<InventorySnapshot> list = inventorySnapshotRepository.queryList(params);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(e -> {
            InventorySnapshotBO bo = new InventorySnapshotBO();
            BeanUtils.copyProperties(e, bo);
            if (e.getId() != null) {
                bo.setId(e.getId().get());
            }
            return bo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean checkTransferInStock(TransferTransitStockRequest request) {
        // 校验在途库存：source 单号存在即视为有效
        if (request == null || request.getSourceOrderNo() == null) {
            log.warn("InventoryDomainServiceImpl.checkTransferInStock: request or sourceOrderNo is null");
            return false;
        }
        return true;
    }

    @Override
    public boolean batchUpdateInTransitStock(List<InventorySupplyBO> supplyBOList) {
        if (CollectionUtils.isEmpty(supplyBOList)) {
            return false;
        }
        List<InventorySupply> entities = supplyBOList.stream().map(bo -> {
            InventorySupply e = new InventorySupply();
            BeanUtils.copyProperties(bo, e);
            return e;
        }).collect(Collectors.toList());
        return inventorySupplyRepository.batchUpdateBySourceOrderNo(entities);
    }

    @Override
    public boolean batchCreateInTransitStock(List<CreateInTransitStockRequest> requestList) {
        if (CollectionUtils.isEmpty(requestList)) {
            return false;
        }
        List<InventorySupply> entities = requestList.stream().map(req -> {
            InventorySupply e = new InventorySupply();
            BeanUtils.copyProperties(req, e);
            return e;
        }).collect(Collectors.toList());
        return inventorySupplyRepository.batchStore(entities);
    }

    @Override
    public boolean transferInTransitStock(TransferTransitStockRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getMaterialDTOList())) {
            log.warn("transferInTransitStock: request or materialDTOList is empty");
            return false;
        }
        List<InventorySupplyBO> supplyBOList = request.getMaterialDTOList().stream().map(dto -> {
            InventorySupplyBO bo = new InventorySupplyBO();
            bo.setMaterialCode(dto.getMaterialCode());
            bo.setSourceOrderNo(request.getSourceOrderNo());
            bo.setTenantId(request.getTenantId());
            return bo;
        }).collect(Collectors.toList());
        return batchUpdateInTransitStock(supplyBOList);
    }
}

