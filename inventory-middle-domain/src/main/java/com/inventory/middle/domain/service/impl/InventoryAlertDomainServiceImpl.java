package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;

import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.converter.InventoryAlertBizConverter;
import com.inventory.middle.domain.service.InventoryAlertDomainService;
import com.inventory.middle.domain.service.LogicalPlantDomainService;
import com.inventory.middle.client.enums.monitor.MonitorRuleSendModeEnum;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.common.util.DateUtils;
import com.inventory.middle.domain.model.bo.logicalPlant.ListLogicalPlantByIdListRequestBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryAlertBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryAlertNotificationBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.service.InventoryAlertCoreService;
import com.inventory.middle.domain.service.impl.InventorySnapshotCoreService;
import com.inventory.middle.domain.service.InventoryAlertNotificationCoreService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Service
@Slf4j
public class InventoryAlertDomainServiceImpl implements InventoryAlertDomainService {

    @Resource
    private LogicalPlantDomainService logicalPlantDomainService;
    @Resource
    private InventoryAlertCoreService alertCoreService;
    @Resource
    private InventoryAlertNotificationCoreService notificationCoreService;
    @Resource
    private SnowflakeGenerator snowflakeGenerator;
    @Resource
    private InventorySnapshotCoreService inventorySnapshotCoreService;
    /**
     * 同一物料预警间隔时间（单位：分钟；默认：60分钟）
     */
    @Value("${alert.interval.minute:60}")
    private String intervalTime;

    @Override
    public boolean createAlertAndNotification(List<InventorySnapshotBO> alertMaterial, InventoryMonitorRuleBO ruleBO, Map<String, BigDecimal> alertKeyDeviateMap) {
        //1、保存预警日志
        List<InventoryAlertBO> alertBOS = InventoryAlertBizConverter.convertSnapshotPosToCreateAlertBos(alertMaterial, ruleBO, alertKeyDeviateMap);
        alertBOS.stream().filter(Objects::nonNull).forEach(e -> e.setId(snowflakeGenerator.next()));
        List<List<InventoryAlertBO>> alertLists = Lists.partition(alertBOS, CommonConstant.PARTITION_SIZE);
        List<InventoryAlertBO> finalAlertBOS = Lists.newArrayList();
        List<InventoryAlertBO> newAlertList = new ArrayList<>();
        for (List<InventoryAlertBO> alertList : alertLists) {
            //1.1 在指定时间内不再创建通知
            alertList = filterByInterval(alertList, ruleBO);
            if (CollectionUtils.isEmpty(alertList)){
                continue;
            }
            finalAlertBOS.addAll(alertList);
            //删除掉之前无用的报警日志
            alertCoreService.deleteByMaterialAndPlant(alertList);
            //插入新的报警日志
            List<InventoryAlertBO> newAlertPOList = alertCoreService.insertBatch(alertList);
            newAlertList.addAll(newAlertPOList);
            if (newAlertPOList.size() == 0){
                log.error("AbstractMonitorHandleChain.createAlertAndNotification insertBatch alert error. alertList={}", JSON.toJSONString(alertList));
                return false;
            }
        }
        if (CollectionUtils.isEmpty(finalAlertBOS)){
            return true;
        }
        List<Long> logicalPlanIds = alertMaterial.stream().filter(e -> Objects.nonNull(e.getLogicalPlantId()))
                .map(InventorySnapshotBO::getLogicalPlantId).distinct().collect(Collectors.toList());
        Map<Long, LogicalPlantBO> plantMap = getLogicalPlantMap(logicalPlanIds);
        List<InventoryAlertNotificationBO> notificationBOS = InventoryAlertBizConverter.convertToCreateNotificationBos(finalAlertBOS, ruleBO, plantMap);
        //2、保存预警日志记录
        List<List<InventoryAlertNotificationBO>> notificationLists = Lists.partition(notificationBOS, CommonConstant.PARTITION_SIZE);
        for (List<InventoryAlertNotificationBO> list : notificationLists) {
            boolean result = notificationCoreService.insertBatch(list);
            if (!result){
                log.error("AbstractMonitorHandleChain.createAlertAndNotification insertBatch notification error. list={}", JSON.toJSONString(list));
                return false;
            }
        }
        // 3，通知 snapshot 新的报警日志已经生成，
        notifySnapshotAlertCreated(newAlertList);
        return true;
    }

    //这里应当是个领域事件通知，先就这么凑合写吧
    private void notifySnapshotAlertCreated(List<InventoryAlertBO> newAlertList) {
        inventorySnapshotCoreService.saveAlertInfo(newAlertList);
    }
    //这里应当是个领域事件通知，先就这么凑合写吧
    private void notifySnapshotAlertDeleted(List<Long> alertIdList) {
        List<InventoryAlertBO> poList = alertIdList.stream().map(e -> {
            InventoryAlertBO po = new InventoryAlertBO();
            po.setId(e);
            return po;
        }).collect(Collectors.toList());
        inventorySnapshotCoreService.deleteAlertId(poList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAlertByRule(InventoryMonitorRuleBO ruleBO){
        List<InventoryAlertBO> alertBOS = alertCoreService.queryByMonitorRuleIds(Lists.newArrayList(ruleBO.getId()));
        if (CollectionUtils.isEmpty(alertBOS)) {
            return true;
        }
        List<Long> alertIds = alertBOS.stream().filter(Objects::nonNull).map(InventoryAlertBO::getId).collect(Collectors.toList());
        boolean deleteAlert = deleteAlertAndNotification(ruleBO, alertIds);
        if (!deleteAlert){
            log.error("MonitorRuleDeleteHandleChain.deleteAlertAndNotification error. ruleBO={},alertIds={}",
                    JSON.toJSONString(ruleBO), JSON.toJSONString(alertIds));
            throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_ALERT_DELETE_FAILED);
        }
        notifySnapshotAlertDeleted(alertIds);
        return true;
    }

    @Override
    public boolean deleteAlertAndNotification(InventoryMonitorRuleBO ruleBO, List<Long> alertIds){
        InventoryAlertBO alertBO = InventoryAlertBizConverter.buildDeleteAlertBo(ruleBO);
        InventoryAlertNotificationBO notificationBO = InventoryAlertBizConverter.buildDeleteNotificationBO(ruleBO);
        //1、删除预警日志
        boolean deleteAlert = alertCoreService.deleteByIds(alertBO, alertIds);
        if (!deleteAlert){
            log.error("AbstractMonitorHandleChain.deleteAlertAndNotification deleteAlert error. alertBO={}", JSON.toJSONString(alertBO));
            return false;
        }
        //2、删除预警日志记录
        boolean deleteResult = notificationCoreService.deleteByAlertIds(notificationBO, alertIds);
        if (!deleteResult){
            log.error("AbstractMonitorHandleChain.deleteAlertAndNotification deleteNotification error. notificationBO={},alertIds={}",
                    JSON.toJSONString(notificationBO), JSON.toJSONString(alertIds));
            return false;
        }
        notifySnapshotAlertDeleted(alertIds);
        return true;
    }

    /**
     * 根据配置的时间，在此时间范围内存在已告警的物料，该物料将不会产生新的告警
     * 即：在配置时间内，同一物料不会再次告警
     * @param alertBOS
     * @param ruleBO
     * @return
     */
    private List<InventoryAlertBO> filterByInterval(List<InventoryAlertBO> alertBOS, InventoryMonitorRuleBO ruleBO){
        if (CollectionUtils.isEmpty(alertBOS) || StringUtils.isEmpty(intervalTime)){
            return alertBOS;
        }
        if (!MonitorRuleSendModeEnum.EMAIL.name().equals(ruleBO.getMonitorSendMode())){
            return alertBOS;
        }
        int minutes = Integer.parseInt(intervalTime.trim());
        if (minutes <= CommonConstant.NUM_ZERO){
            return alertBOS;
        }
        InventoryAlertBO bo = new InventoryAlertBO();
        bo.setTenantId(ruleBO.getTenantId());
        bo.setAlertDate(DateUtils.getBeforeOrAfterDateByMinutes(LocalDateTime.now(), -minutes));

        List<String> materialCodes = alertBOS.stream().map(InventoryAlertBO::getMaterialCode).collect(Collectors.toList());
        List<InventoryAlertBO> list = alertCoreService.queryByMaterialCodes(materialCodes, bo);
        if (CollectionUtils.isEmpty(list)){
            return alertBOS;
        }
        List<String> targetList = list.stream().map(InventoryAlertBO::getMaterialCode).collect(Collectors.toList());
        return alertBOS.stream().filter(e -> !targetList.contains(e.getMaterialCode())).collect(Collectors.toList());
    }



    private Map<Long, LogicalPlantBO> getLogicalPlantMap(List<Long> logicalPlantIds){
        ListLogicalPlantByIdListRequestBO requestBO = new ListLogicalPlantByIdListRequestBO();
        requestBO.setIdList(logicalPlantIds);
        List<LogicalPlantBO> logicalPlantPOList = logicalPlantDomainService.listByPlantIdList(requestBO);
        if (CollectionUtils.isEmpty(logicalPlantPOList)){
            return Maps.newHashMap();
        }

        return logicalPlantPOList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(LogicalPlantBO::getId, Function.identity(), (key1, key2) -> key2));
    }

}
