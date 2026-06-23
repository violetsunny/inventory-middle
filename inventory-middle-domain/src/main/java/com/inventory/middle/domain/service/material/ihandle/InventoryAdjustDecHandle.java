/**
 * kll Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.service.external.MaterialDocOutboundService;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.impl.InventorySnapshotCoreService;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 出库调减
 *
 * @author kll
 * @version $Id: AdjustInventoryDecHandle, v 0.1 2021/6/24 21:21 Exp $
 */
@Service
@Slf4j
public class InventoryAdjustDecHandle implements IHandler {

    @Resource
    private MaterialDocOutboundService materialDocOutboundDefaultHandlerService;
    @Resource
    protected InventorySnapshotCoreService inventorySnapshotCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "出库调减")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentOut = req.getMaterialDocumentOut();
        Stopwatch sw1 = Stopwatch.createUnstarted();
        sw1.start();
        Long newMaterialDocId = materialDocumentOut.getMaterialDocId();
        // 是否需要限制批次内出库
        Boolean needBatchNo = needBatchNoInner(materialDocumentOut);
        // 校验出库数据
        materialDocOutboundDefaultHandlerService.checkAdjustNumber(materialDocumentOut, needBatchNo);
        sw1.stop();
        log.info("createMaterialDoc InventoryAdjustDecHandle checkAdjustNumber数据 {}", sw1.toString());

        Stopwatch sw2 = Stopwatch.createUnstarted();
        sw2.start();
        List<MaterialDocumentItemBO> list = new ArrayList<>();
        for (MaterialDocumentItemBO itemBO : materialDocumentOut.getMaterialDocumentItems()) {
            // 判断数量
            Integer stockType = Optional.ofNullable(itemBO.getWarehouseData().getDemandStockType()).orElse(StockTypeEnum.UNRESTRICTED.getCode());
            StockTypeEnum stockTypeEnum = StockTypeEnum.getByCode(stockType);
            List<InventorySnapshotBO> inventorySnapshotPOS = inventorySnapshotCoreService.allAvailableSortList(itemBO.getMaterialData().getMaterialCode(),
                materialDocumentOut.getDemandLogicalPlantNo(), stockTypeEnum, materialDocumentOut.getTenantId(), itemBO.getWarehouseData().getDemandStorageLocationNo());

            materialDocOutboundDefaultHandlerService.doBiz(materialDocumentOut.getOperator(), itemBO, inventorySnapshotPOS, stockTypeEnum, newMaterialDocId, list, needBatchNo);
        }
        log.info("createMaterialDoc InventoryAdjustDecHandle 生成出items {}", JSON.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ResponseCodeEnum.OUT_ERROR,"实际出库item为空");
        }
        materialDocumentOut.setMaterialDocumentItems(list);
        sw2.stop();
        log.info("createMaterialDoc InventoryAdjustDecHandle 出库 {}", sw2.toString());
        return message;
    }

    private Boolean needBatchNoInner(MaterialDocumentBO materialDocumentOut) {
        return Lists
            .newArrayList(MaterialAdjustTypeEnum.ZY501.getCode(), MaterialAdjustTypeEnum.ZY502.getCode(),
                MaterialAdjustTypeEnum.ZY503.getCode(), MaterialAdjustTypeEnum.ZY504.getCode(),
                MaterialAdjustTypeEnum.ZY505.getCode(), MaterialAdjustTypeEnum.ZY506.getCode())
            .contains(materialDocumentOut.getAdjustType());
    }
}
