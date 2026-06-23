/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material;

import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.ihandle.*;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 出入库 操作原有批次 库内转移
 * @author kll
 * @version $Id: MaterialDocInOutHandleChain, v 0.1 2021/6/21 15:23 Exp $
 */
@Service("materialDocInOutOriginalHandleChain")
public class MaterialDocInOutOriginalHandleChain implements IHandleChain {
    @Resource
    private MaterialBatchDataOutOriginalHandle materialBatchDataOutOriginalHandle;
    @Resource
    private InventoryAdjustDecHandle inventoryAdjustDecHandle;
    @Resource
    private MaterialDocOutHandle materialDocOutHandle;
    @Resource
    private MaterialBatchDataInOriginalHandle materialBatchDataInOriginalHandle;
    @Resource
    private MaterialDataInOutHandle materialDataInOutHandle;
    @Resource
    private InventoryAdjustIncOriginalHandle inventoryAdjustIncOriginalHandle;
    @Resource
    private MaterialDocInHandle materialDocInHandle;
    @Resource
    private MaterialDocInOutOriginalTmHandle materialDocInOutOriginalTmHandle;
    @Resource
    private InventoryOutMQHandle inventoryOutMQHandle;
    @Resource
    private InventoryInMQHandle inventoryInMQHandle;

    /**
     * 待处理handler
     */
    private List<IHandler> handlers;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doProcess(HandleMessage msg) {
        if(!CollectionUtils.isEmpty(handlers)){
            for(IHandler handler:handlers){
                handler.operation(msg);
            }
        }

        return true;
    }

    @PostConstruct
    void init() {
        handlers = Lists.newArrayList();
        //出库
        handlers.add(materialBatchDataOutOriginalHandle);
        handlers.add(inventoryAdjustDecHandle);
        //出库凭证
        handlers.add(materialDocOutHandle);

        //入库
        handlers.add(materialBatchDataInOriginalHandle);
        //出入库数据
        handlers.add(materialDataInOutHandle);
        handlers.add(inventoryAdjustIncOriginalHandle);
        //入库凭证
        handlers.add(materialDocInHandle);
        //事务提交数据
        handlers.add(materialDocInOutOriginalTmHandle);
        //MAP
        handlers.add(inventoryOutMQHandle);
        handlers.add(inventoryInMQHandle);
    }
}
