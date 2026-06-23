package com.inventory.middle.domain.service.map.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;
import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.repository.InventoryMapHisRepository;
import com.inventory.middle.domain.repository.InventoryMapRepository;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.domain.service.external.RemoteInventoryMapService;
import com.inventory.middle.domain.service.map.InventoryMapDomainService;
import com.inventory.middle.domain.service.map.bo.InventoryMapBO;
import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.client.dto.map.QueryInventoryMapDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 移动平均价领域服务实现
 * 迁移自: com.enn.inventory.center.ext.biz.service.impl.InventoryMapBizServiceImpl
 *
 * <p>移动平均价（MAP）计算公式：
 * <pre>
 *   新MAP = (旧MAP × 旧库存总数 + 本次入库价格 × 本次入库数量) / (旧库存总数 + 本次入库数量)
 *
 *   其中：
 *     - 旧库存总数 = SUM(unrestricted + damaged + inspection) 当前快照所有批次
 *     - 旧MAP      = inventory_map 表中 skuCode+logicalPlantNo 的最新记录
 *     - 本次入库价格 = 关联批次快照的 batchPrice（按 exchangeRate 折算）
 *     - 本次入库数量 = 关联批次快照的 unrestricted 数量
 *     - 若旧库存为 0，则新MAP = 入库价格
 * </pre>
 */
@Service
@Slf4j
public class InventoryMapDomainServiceImpl implements InventoryMapDomainService {

    @Resource
    private RemoteInventoryMapService remoteInventoryMapService;

    @Resource
    private InventoryMapRepository inventoryMapRepository;

    @Resource
    private InventoryMapHisRepository inventoryMapHisRepository;

    @Resource
    private InventorySnapshotRepository inventorySnapshotRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cal(InventoryChangeMessage message) throws Exception {
        if (!Boolean.TRUE.equals(message.getCalMap())) {
            return;
        }
        log.info("InventoryMapDomainService.cal begin, message={}", JSON.toJSONString(message));

        String materialCode = message.getMaterialCode();
        String logicalPlantNo = message.getLogicalPlantNo();
        String tenantId = message.getTenantId();
        String mapCode = message.getMapCode();
        String mapSubCode = message.getMapSubCode();
        String currency = message.getCurrency();
        BigDecimal exchangeRate = message.getExchangeRate();
        if (exchangeRate == null || exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
            exchangeRate = BigDecimal.ONE;
        }

        // ===== Step 1: 查询当前批次快照（本次入库），获取入库价格和数量 =====
        // 按 mapSubCode 对应的批次快照（batchNo = mapSubCode）
        Map<String, Object> batchParams = new HashMap<>();
        batchParams.put("materialCode", materialCode);
        batchParams.put("logicalPlantNo", logicalPlantNo);
        batchParams.put("tenantId", tenantId);
        if (mapSubCode != null && !mapSubCode.isEmpty()) {
            batchParams.put("batchNo", mapSubCode);
        }
        List<InventorySnapshot> batchSnapshots = inventorySnapshotRepository.queryList(batchParams);

        BigDecimal inboundPrice = BigDecimal.ZERO;   // 本次入库价格（折算后）
        BigDecimal inboundQty = BigDecimal.ZERO;     // 本次入库数量

        if (!CollectionUtils.isEmpty(batchSnapshots)) {
            InventorySnapshot batchSnap = batchSnapshots.get(0);
            if (batchSnap.getBatchPrice() != null && batchSnap.getBatchPrice().compareTo(BigDecimal.ZERO) > 0) {
                // 按汇率折算为本位币
                inboundPrice = batchSnap.getBatchPrice().multiply(exchangeRate)
                        .setScale(6, RoundingMode.HALF_UP);
            }
            inboundQty = calcTotalQty(batchSnap);
        }

        log.info("InventoryMapDomainService.cal: inboundPrice={} inboundQty={} skuCode={} logicalPlantNo={}",
                inboundPrice, inboundQty, materialCode, logicalPlantNo);

        if (inboundQty.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("InventoryMapDomainService.cal: inboundQty is 0, skip MAP calculation, materialCode={}", materialCode);
            return;
        }

        // ===== Step 2: 查询所有快照，计算旧总库存数量 =====
        Map<String, Object> allParams = new HashMap<>();
        allParams.put("materialCode", materialCode);
        allParams.put("logicalPlantNo", logicalPlantNo);
        allParams.put("tenantId", tenantId);
        List<InventorySnapshot> allSnapshots = inventorySnapshotRepository.queryList(allParams);

        BigDecimal oldTotalQty = BigDecimal.ZERO;
        for (InventorySnapshot snap : allSnapshots) {
            // 排除本次入库批次，计算入库前的旧库存
            if (mapSubCode != null && mapSubCode.equals(snap.getBatchNo())) {
                // 旧库存 = 当前总数 - 本次入库数量
                oldTotalQty = oldTotalQty.add(calcTotalQty(snap)).subtract(inboundQty);
            } else {
                oldTotalQty = oldTotalQty.add(calcTotalQty(snap));
            }
        }
        if (oldTotalQty.compareTo(BigDecimal.ZERO) < 0) {
            oldTotalQty = BigDecimal.ZERO;
        }

        // ===== Step 3: 查询旧 MAP =====
        InventoryMap existingMap = inventoryMapRepository.findBySkuAndPlant(materialCode, logicalPlantNo, tenantId);
        BigDecimal oldMap = (existingMap != null && existingMap.getMap() != null)
                ? existingMap.getMap() : BigDecimal.ZERO;

        // ===== Step 4: 计算新 MAP =====
        BigDecimal newMap;
        BigDecimal totalQtyAfter = oldTotalQty.add(inboundQty);
        if (totalQtyAfter.compareTo(BigDecimal.ZERO) == 0) {
            newMap = inboundPrice;
        } else if (oldTotalQty.compareTo(BigDecimal.ZERO) == 0) {
            // 旧库存为 0：新MAP = 入库价格
            newMap = inboundPrice;
        } else {
            // 标准 MAP 公式
            BigDecimal oldValue = oldMap.multiply(oldTotalQty);
            BigDecimal newValue = inboundPrice.multiply(inboundQty);
            newMap = oldValue.add(newValue)
                    .divide(totalQtyAfter, 6, RoundingMode.HALF_UP);
        }

        log.info("InventoryMapDomainService.cal: oldMap={} oldTotalQty={} inboundPrice={} inboundQty={} newMap={}",
                oldMap, oldTotalQty, inboundPrice, inboundQty, newMap);

        // ===== Step 5: upsert inventory_map =====
        if (existingMap == null) {
            existingMap = new InventoryMap();
            existingMap.setSkuCode(materialCode);
            existingMap.setLogicalPlantNo(logicalPlantNo);
            existingMap.setTenantId(tenantId);
            existingMap.setMapCode(mapCode);
            existingMap.setMapSubCode(mapSubCode);
            existingMap.setCreatorId(message.getOperator());
            existingMap.setCreateTime(LocalDateTime.now());
            existingMap.setDeleted(0);
        }
        existingMap.setMap(newMap);
        existingMap.setUpdateTime(LocalDateTime.now());
        existingMap.setUpdatorId(message.getOperator());
        inventoryMapRepository.store(existingMap);

        // ===== Step 6: insert inventory_map_his =====
        InventoryMapHis his = new InventoryMapHis();
        his.setMapCode(mapCode);
        his.setMapSubCode(mapSubCode);
        his.setSkuCode(materialCode);
        his.setLogicalPlantNo(logicalPlantNo);
        his.setTenantId(tenantId);
        his.setMap(newMap);
        his.setSkuPriceTotal(inboundPrice.multiply(inboundQty).setScale(6, RoundingMode.HALF_UP));
        his.setSkuStockTotal(totalQtyAfter);
        his.setCurrency(currency);
        his.setExchangeRate(exchangeRate);
        if (existingMap.getId() != null) {
            his.setMapId(existingMap.getId().get());
        }
        his.setCreatorId(message.getOperator());
        his.setCreateTime(LocalDateTime.now());
        his.setDeleted(0);
        inventoryMapHisRepository.store(his);

        log.info("InventoryMapDomainService.cal success, skuCode={} logicalPlantNo={} newMap={}",
                materialCode, logicalPlantNo, newMap);
    }

    @Override
    public InventoryMapBO queryInventoryMap(String skuCode, String logicalPlantNo, String tenantId) {
        // 优先从本地 inventory_map 表查询，降低对外部服务的依赖
        InventoryMap localMap = inventoryMapRepository.findBySkuAndPlant(skuCode, logicalPlantNo, tenantId);
        if (localMap != null) {
            InventoryMapBO bo = new InventoryMapBO();
            bo.setSkuCode(localMap.getSkuCode());
            bo.setLogicalPlantNo(localMap.getLogicalPlantNo());
            bo.setMap(localMap.getMap());
            bo.setMapCode(localMap.getMapCode());
            bo.setMapSubCode(localMap.getMapSubCode());
            bo.setTenantId(localMap.getTenantId());
            if (localMap.getId() != null) {
                bo.setId(localMap.getId().get());
            }
            return bo;
        }

        // fallback: 远程查询（inventory-center）
        QueryInventoryMapDTO query = new QueryInventoryMapDTO();
        query.setSkuCode(skuCode);
        query.setLogicalPlantNo(logicalPlantNo);
        query.setTenantId(tenantId);
        SingleResponse<InventoryMapDTO> result = remoteInventoryMapService.queryInventoryMap(query);
        if (Objects.isNull(result) || Objects.isNull(result.getData())) {
            return null;
        }
        InventoryMapDTO dto = result.getData();
        InventoryMapBO bo = new InventoryMapBO();
        bo.setSkuCode(dto.getSkuCode());
        bo.setLogicalPlantNo(dto.getLogicalPlantNo());
        bo.setMap(dto.getMap());
        return bo;
    }

    /**
     * 计算快照中的总库存数量（不限制 + 损坏 + 质检）
     */
    private BigDecimal calcTotalQty(InventorySnapshot snap) {
        BigDecimal qty = BigDecimal.ZERO;
        if (snap.getUnrestricted() != null) qty = qty.add(snap.getUnrestricted());
        if (snap.getDamaged() != null) qty = qty.add(snap.getDamaged());
        if (snap.getInspection() != null) qty = qty.add(snap.getInspection());
        return qty;
    }
}
