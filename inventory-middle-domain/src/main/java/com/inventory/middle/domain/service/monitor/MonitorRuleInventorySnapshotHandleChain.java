/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.monitor;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.service.InventoryMonitorRuleDomainService;
import com.inventory.middle.domain.service.LogicalPlantDomainService;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预警规则创建操作
 * @author dongguo.tao
 * @version
 */
@Slf4j
@Service("monitorRuleInventorySnapshotHandleChain")
public class MonitorRuleInventorySnapshotHandleChain extends AbstractMonitorHandleChain implements IHandleChain<InventoryChangeMessage, Boolean> {

    @Resource
    private InventoryMonitorRuleDomainService monitorRuleDomainService;
    @Resource
    private LogicalPlantDomainService logicalPlantDomainService;

    @Override
    public boolean doProcess(HandleMessage<InventoryChangeMessage, Boolean> msg) {
        log.info("MonitorRuleInventorySnapshotHandleChain.doProcess msg={}", JSON.toJSONString(msg));
        InventoryChangeMessage message = msg.getT();
        if (!validateMessage(message)){
            log.warn("MonitorRuleInventorySnapshotHandleChain.doProcess message validate failed");
            return false;
        }

        LogicalPlantBO logicalPlantPO = logicalPlantDomainService.getByLogicalPlantNo(message.getLogicalPlantNo());
        if (logicalPlantPO == null){
            log.warn("MonitorRuleInventorySnapshotHandleChain.doProcess logicalPlant is null.");
            return false;
        }

        //根据物料和逻辑仓信息查询预警规则
        List<String> monitorObjects = Lists.newArrayList(message.getMaterialCode(), String.valueOf(logicalPlantPO.getId()));
        List<InventoryMonitorRuleBO> monitorRules = monitorRuleDomainService.queryByMonitorObjects(message.getTenantId(), monitorObjects, null);
        //重新计算预警信息
        return reCalculateAlert(monitorRules);

    }

    private boolean reCalculateAlert(List<InventoryMonitorRuleBO> monitorRules){
        if (CollectionUtils.isEmpty(monitorRules)){
            return true;
        }
        for (InventoryMonitorRuleBO ruleBO : monitorRules) {
            if (!isTargetRule(ruleBO)){
                continue;
            }
            boolean result = calculateAlert(ruleBO, null);
            if (!result){
                return false;
            }
        }
        return true;
    }

    private boolean validateMessage(InventoryChangeMessage message){
        if (null == message){
            return false;
        }
        if (StringUtils.isEmpty(message.getTenantId())){
            return false;
        }
        if (StringUtils.isEmpty(message.getMaterialCode())){
            return false;
        }
        if (StringUtils.isEmpty(message.getLogicalPlantNo())){
            return false;
        }
        return true;
    }

}
