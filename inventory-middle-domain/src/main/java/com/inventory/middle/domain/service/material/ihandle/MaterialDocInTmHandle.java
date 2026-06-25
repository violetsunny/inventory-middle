/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.service.material.model.MaterialDocInvRes;
import com.inventory.middle.client.dto.transit.TransferTransitStockRequest;
import com.inventory.middle.domain.service.InventoryDomainService;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.MaterialBatchNoBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.InventoryCoreService;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import com.inventory.middle.domain.service.MaterialDocSubCoreService;
import com.inventory.middle.domain.service.MaterialLogicalPlantRefCoreService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.COMMA;

/**
 * 数据提交事务管理
 *
 * @author kanglele
 * @version $Id: MaterialDocInTmHandle, v 0.1 2021/8/20 10:06 Exp $
 */
@Service
@Slf4j
public class MaterialDocInTmHandle implements IHandler {
    @Resource
    private InventoryCoreService inventoryCoreService;
    @Resource
    private MaterialDocCoreService materialDocCoreService;
    @Resource
    private MaterialDocSubCoreService materialDocSubCoreService;
    @Resource
    private InventoryDomainService inventoryDomainService;
    @Resource
    private MaterialLogicalPlantRefCoreService materialLogicalPlantRefCoreService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "入库数据提交")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentIn = req.getMaterialDocumentIn();

        Stopwatch sw_1 = Stopwatch.createUnstarted();
        sw_1.start();
        List<CreateMaterialLogicalPlantRefBO> creteBOList = req.getMaterialLogicalPlantRefs();
        materialLogicalPlantRefCoreService.batchCreate(creteBOList);
        sw_1.stop();
        log.info("createMaterialDoc MaterialDocInTmHandle 创建物料和库存关系 {}", sw_1.toString());

        Stopwatch sw0 = Stopwatch.createUnstarted();
        sw0.start();
        TransferTransitStockRequest request = req.getTransitStockRequest();
        if(Objects.nonNull(request)){
            inventoryDomainService.transferInTransitStock(request);
        }
        sw0.stop();
        log.info("createMaterialDoc MaterialDocInTmHandle 调整在途 {}", sw0.toString());

        Stopwatch sw1 = Stopwatch.createUnstarted();
        sw1.start();
        inventoryCoreService.adjustInventoryInc(req.getInventorySupplys());
        sw1.stop();
        log.info("createMaterialDoc MaterialDocInTmHandle adjustInventoryInc数据库 {}", sw1.toString());

        Stopwatch sw2 = Stopwatch.createUnstarted();
        sw2.start();
        materialDocumentIn = materialDocCoreService.createMaterialDoc(materialDocumentIn);
        sw2.stop();
        log.info("createMaterialDoc MaterialDocInTmHandle createMaterialDoc主项 {}", sw2.toString());

        Stopwatch sw3 = Stopwatch.createUnstarted();
        sw3.start();
        //补充子项信息
        materialDocSubCoreService.supplementMaterialDoc(materialDocumentIn);
        sw3.stop();
        log.info("createMaterialDoc MaterialDocInTmHandle supplementMaterialDoc子项 {}", sw3.toString());

        //将返回传出
        MaterialDocInvRes res = new MaterialDocInvRes();
        res.setMaterialDocNo(materialDocumentIn.getMaterialDocNo());
        List<MaterialBatchNoBO> materialBatchNos = materialDocumentIn.getMaterialDocumentItems().stream().map(d->{
            MaterialBatchNoBO materialBatchNoBO = new MaterialBatchNoBO();
            materialBatchNoBO.setMaterialCode(d.getMaterialCode());
            materialBatchNoBO.setBatchNo(d.getBatchNo());
            materialBatchNoBO.setBatchPrice(d.getQuantityData().getPrice());
            return materialBatchNoBO;
        }).collect(Collectors.toList());
        res.setMaterialBatchNos(materialBatchNos);
        Set<String> batchNos = materialBatchNos.stream().map(MaterialBatchNoBO::getBatchNo).collect(Collectors.toSet());
        res.setBatchNo(StringUtils.join(batchNos,COMMA));
        res.setFullMaterialDocNo(materialDocumentIn.getMaterialDocNo());
        return message.setE((E)res);
    }

}
