/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 出库库存变化MQ
 *
 * @author kll
 * @version $Id: InventoryMAPHandle, v 0.1 2021/6/24 11:40 Exp $
 */
@Service
@Slf4j
public class InventoryOutMQHandle implements IHandler {

    @Resource
    private MaterialDocCoreService materialDocCoreService;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "出库库存变化MQ")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentOut = req.getMaterialDocumentOut();
        materialDocCoreService.sendInventoryInMQ(materialDocumentOut, InventoryTagEnum.OUTBOUND);
        return message;
    }
}
