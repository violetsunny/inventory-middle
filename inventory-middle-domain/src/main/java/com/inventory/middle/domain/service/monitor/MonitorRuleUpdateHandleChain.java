/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.monitor;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 预警规则创建操作
 * @author dongguo.tao
 * @version
 */
@Slf4j
@Service("monitorRuleUpdateHandleChain")
public class MonitorRuleUpdateHandleChain extends AbstractMonitorHandleChain implements IHandleChain<MonitorMessageBO, Boolean> {

    @Override
    public boolean doProcess(HandleMessage<MonitorMessageBO, Boolean> msg) {
        log.info("MonitorRuleCreateUpdateHandleChain.doProcess msg={}", JSON.toJSONString(msg));
        if (Objects.isNull(msg.getT())) {
            return true;
        }
        MonitorMessageBO messageBO = msg.getT();
        //查询预警规则
        InventoryMonitorRuleBO ruleBO = getMonitorRule(messageBO.getMonitorRuleId());
        if (Objects.isNull(ruleBO)) {
            return true;
        }
        if (messageBO.getChangedDimension()){
            //预警规则的维度发生变化时，计算预警信息
            return calculateAlert(ruleBO, null);
        }
        return true;
    }

}
