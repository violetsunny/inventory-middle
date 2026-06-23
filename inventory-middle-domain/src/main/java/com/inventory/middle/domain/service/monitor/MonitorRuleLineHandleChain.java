/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.monitor;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.service.InventoryAlertDomainService;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 预警规则创建操作
 * @author dongguo.tao
 * @version
 */
@Slf4j
@Service("monitorRuleLineHandleChain")
public class MonitorRuleLineHandleChain extends AbstractMonitorHandleChain implements IHandleChain<MonitorMessageBO, Boolean> {
    @Resource
    private InventoryAlertDomainService alertDomainService;

    @Override
    public boolean doProcess(HandleMessage<MonitorMessageBO, Boolean> msg) {
        log.info("MonitorRuleLineHandleChain.doProcess msg={}", JSON.toJSONString(msg));
        if (Objects.isNull(msg.getT())) {
            msg.setE(true);
            return true;
        }
        MonitorMessageBO messageBO = msg.getT();
        if (CollectionUtils.isEmpty(messageBO.getRuleLineIds()) && CollectionUtils.isEmpty(messageBO.getDeletedRuleLineIds())){
            return true;
        }
        //查询预警规则
        InventoryMonitorRuleBO ruleBO = getMonitorRule(messageBO.getMonitorRuleId());
        if (Objects.isNull(ruleBO)) {
            msg.setE(true);
            return true;
        }
        boolean result = false;
        if (!CollectionUtils.isEmpty(messageBO.getDeletedRuleLineIds())){
            result = alertDomainService.deleteAlertAndNotification(ruleBO, messageBO.getDeletedRuleLineIds());
        }
        if (CollectionUtils.isEmpty(messageBO.getRuleLineIds())){
            return result;
        }
        //计算预警
        result = calculateAlert(ruleBO, messageBO.getRuleLineIds());
        msg.setE(result);
        return result;
    }

}
