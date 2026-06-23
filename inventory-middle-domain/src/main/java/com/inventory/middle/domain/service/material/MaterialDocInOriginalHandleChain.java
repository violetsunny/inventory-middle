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
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 入库操作 更新现有批次 盘盈
 *
 * @author kll
 * @version $Id: MaterialDocBizHandleChain, v 0.1 2021/6/21 9:28 Exp $
 */
@Service("materialDocInOriginalHandleChain")
public class MaterialDocInOriginalHandleChain implements IHandleChain {

    @Resource
    private MaterialBatchDataInOriginalHandle materialBatchDataInOriginalHandle;
    @Resource
    private InventoryAdjustIncOriginalHandle inventoryAdjustIncOriginalHandle;
    @Resource
    private MaterialDocInHandle materialDocInHandle;
    @Resource
    private MaterialDocInOriginalTmHandle materialDocInOriginalTmHandle;
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
        //原有批次
        handlers.add(materialBatchDataInOriginalHandle);
        //原有批次库存调增
        handlers.add(inventoryAdjustIncOriginalHandle);
        //生成物料凭证
        handlers.add(materialDocInHandle);
        //事务提交数据
        handlers.add(materialDocInOriginalTmHandle);
        //MAP
        handlers.add(inventoryInMQHandle);
    }

}
