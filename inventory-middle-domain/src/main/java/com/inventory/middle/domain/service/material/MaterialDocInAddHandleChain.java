/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.ihandle.*;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 入库操作 增加批次  期初导入，采购入库
 *
 * @author kll
 * @version $Id: MaterialDocBizHandleChain, v 0.1 2021/6/21 9:28 Exp $
 */
@Slf4j
@Service(value = "materialDocInAddHandleChain")
public class MaterialDocInAddHandleChain implements IHandleChain {
    @Resource
    private MaterialDocSpecialAdjustHandle materialDocSpecialAdjustHandle;
    @Resource
    private MaterialBatchDataInHandle materialBatchDataInHandle;
    @Resource
    private InventoryAdjustIncHandle inventoryAdjustIncHandle;
    @Resource
    private MaterialDocInHandle materialDocInHandle;
    @Resource
    private MaterialDocInTmHandle materialDocInTmHandle;
    @Resource
    private MaterialBatchSysHandle materialBatchSysHandle;
    @Resource
    private InventoryInMQHandle inventoryInMQHandle;

    /**
     * 待处理handler
     */
    private List<IHandler> handlers;

    @Override
    public boolean doProcess(HandleMessage msg) {
        if (!CollectionUtils.isEmpty(handlers)) {
            for (IHandler handler : handlers) {
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
        //生成批次
        handlers.add(materialBatchDataInHandle);
        //库存调增
        handlers.add(inventoryAdjustIncHandle);
        //生成物料凭证
        handlers.add(materialDocInHandle);
        //事务提交数据
        handlers.add(materialDocInTmHandle);
        //批次同步
        handlers.add(materialBatchSysHandle);
        //MAP
        handlers.add(inventoryInMQHandle);
    }

}
