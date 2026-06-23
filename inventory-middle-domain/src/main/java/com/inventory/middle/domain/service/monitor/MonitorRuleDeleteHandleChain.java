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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 预警规则创建操作
 * @author dongguo.tao
 * @version
 */
@Slf4j
@Service("monitorRuleDeleteHandleChain")
public class MonitorRuleDeleteHandleChain extends AbstractMonitorHandleChain implements IHandleChain<Long, Boolean> {
    @Resource
    private InventoryAlertDomainService alertDomainService;

    @Override
    public boolean doProcess(HandleMessage<Long, Boolean> msg) {
        log.info("MonitorRuleDeleteHandleChain.doProcess msg={}", JSON.toJSONString(msg));
        if (Objects.isNull(msg.getT())) {
            msg.setE(true);
            return true;
        }
        InventoryMonitorRuleBO ruleBO = getMonitorRule(msg.getT());
        if (Objects.isNull(ruleBO) ) {
            msg.setE(true);
            return true;
        }
        return alertDomainService.deleteAlertByRule(ruleBO);
    }

}
