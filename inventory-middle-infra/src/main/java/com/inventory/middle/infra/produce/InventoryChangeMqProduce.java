/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.infra.produce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.infra.produce.model.InventoryChangeMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kll
 * @version $Id: InventoryChangeMqProduct, v 0.1 2021/6/24 12:40 Exp $
 */
@Component
@Slf4j
public class InventoryChangeMqProduce {

    @Value("${rocketmq.topic:inventory-middle-topic}")
    protected String topic;

    /**
     * @param produce
     * @param inventoryTagEnum
     */
    public void doSend(InventoryChangeMessage produce, InventoryTagEnum inventoryTagEnum) {
        try {
            log.info("InventoryChangeMqProduce send MQ tag:{} req:{} result={}",inventoryTagEnum.getCode(), JSON.toJSONString(produce), true);
        } catch (Exception ex) {
            log.error("InventoryChangeMqProduce send MQ msg Error ", ex);
        }
    }
}
