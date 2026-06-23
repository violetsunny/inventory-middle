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
 * 出入库 创建新的批次  调拨 TODO 事务
 * @author kll
 * @version $Id: MaterialDocInOutHandleChain, v 0.1 2021/6/21 15:23 Exp $
 */
@Service("materialDocInOutAddHandleChain")
public class MaterialDocInOutAddHandleChain implements IHandleChain {
    @Resource
    private MaterialDocSpecialAdjustHandle materialDocSpecialAdjustHandle;
    @Resource
    private MaterialBatchDataOutOriginalHandle materialBatchDataOutOriginalHandle;
    @Resource
    private InventoryAdjustDecHandle inventoryAdjustDecHandle;
    @Resource
    private MaterialDocOutHandle materialDocOutHandle;
    @Resource
    private MaterialBatchDataInHandle materialBatchDataInHandle;
    @Resource
    private MaterialDataInOutHandle materialDataInOutHandle;
    @Resource
    private InventoryAdjustIncHandle inventoryAdjustIncHandle;
    @Resource
    private MaterialDocInHandle materialDocInHandle;
    @Resource
    private MaterialDocInOutTmHandle materialDocInOutTmHandle;
    @Resource
    private InventoryOutMQHandle inventoryOutMQHandle;
    @Resource
    private MaterialBatchSysHandle materialBatchSysHandle;
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
        //特殊库位处理
        handlers.add(materialDocSpecialAdjustHandle);
        //出库操作
        handlers.add(materialBatchDataOutOriginalHandle);
        handlers.add(inventoryAdjustDecHandle);
        //出库凭证
        handlers.add(materialDocOutHandle);
        //入库操作
        //批次数据
        handlers.add(materialBatchDataInHandle);
        //出入库数据
        handlers.add(materialDataInOutHandle);
        //库存调增
        handlers.add(inventoryAdjustIncHandle);
        //入库物料凭证
        handlers.add(materialDocInHandle);
        //事务提交数据
        handlers.add(materialDocInOutTmHandle);
        //出MAP
        handlers.add(inventoryOutMQHandle);
        //批次同步
        handlers.add(materialBatchSysHandle);
        //入MAP
        handlers.add(inventoryInMQHandle);
    }
}
