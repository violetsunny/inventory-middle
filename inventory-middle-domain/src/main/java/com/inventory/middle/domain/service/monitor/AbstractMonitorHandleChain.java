package com.inventory.middle.domain.service.monitor;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.*;
import com.inventory.middle.client.enums.monitor.MonitorRuleDimensionEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleTypeEnum;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.inventorysnapshot.MonitorInventorySnapshotPageBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryAlertBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import com.inventory.middle.domain.model.bo.mq.MonitorQuantityMessageBO;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import com.inventory.middle.domain.service.InventoryAlertCoreService;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dongguo
 */
@Slf4j
public abstract class AbstractMonitorHandleChain {

    @Resource
    private InventoryAlertDomainService alertDomainService;
    @Resource
    private InventoryAlertCoreService alertCoreService;
    @Resource
    private InventoryMonitorRuleLineDomainService monitorRuleLineDomainService;
    @Resource
    private InventorySnapshotDomainService snapshotDomainService;
    @Resource
    private InventoryMonitorRuleDomainService monitorRuleDomainService;
    @Resource
    private InventoryMqProducer monitorMqProduce;
    @Resource
    private InventoryMqProducer monitorQuantityMqProduce;


    protected InventoryMonitorRuleBO getMonitorRule(Long monitorRuleId){
        InventoryMonitorRuleBO ruleBO = monitorRuleDomainService.queryById(monitorRuleId);
        return isTargetRule(ruleBO) ? ruleBO : null;
    }

    protected boolean isTargetRule(InventoryMonitorRuleBO ruleBO){
        if (Objects.isNull(ruleBO)) {
            return false;
        }
        // 目前只支持数量预警
        if (!StringUtils.equals(MonitorRuleTypeEnum.QUANTITY.name(), ruleBO.getMonitorType())){
            return false;
        }
        return true;
    }

    /**
     * 计算预警信息
     * @param ruleBO
     * @return
     */
    protected boolean calculateAlert(InventoryMonitorRuleBO ruleBO, List<Long> ruleLineIds){
        try {
            if (Objects.isNull(ruleBO)) {
                log.info("AbstractMonitorHandleChain.calculateAlert ruleBO is null or disable. ruleBO={}", JSON.toJSONString(ruleBO));
                return true;
            }
            if (validateDeleteAlert(ruleBO)){
                return alertDomainService.deleteAlertByRule(ruleBO);
            }
            List<InventoryMonitorRuleLineBO> lineBOS ;
            if (CollectionUtils.isEmpty(ruleLineIds)){
                lineBOS = monitorRuleLineDomainService.queryByMonitorRuleIds(Lists.newArrayList(ruleBO.getId()));
            } else {
                lineBOS = monitorRuleLineDomainService.queryByIds(ruleLineIds);
            }
            if (CollectionUtils.isEmpty(lineBOS)){
                return true;
            }
            List<InventorySnapshotBO> snapshotPOList = getInventorySnapshot(ruleBO, lineBOS);
            if (CollectionUtils.isEmpty(snapshotPOList)) {
                return true;
            }
            switch (MonitorRuleDimensionEnum.getByName(ruleBO.getMonitorDimension())){
                case LOGICAL_PLANT_MATERIAL:
                    return alertByLogicalPlantMaterial(ruleBO, lineBOS, snapshotPOList);
                case ASSIGN_MATERIAL:
                    return alertByAssignMaterial(ruleBO, lineBOS, snapshotPOList);
                default:
                    return true;
            }
        } catch (Exception e) {
            log.error("AbstractMonitorHandleChain.calculateAlert error. ruleBO={}", JSON.toJSONString(ruleBO), e);
            return false;
        }
    }

    /**
     * 验证是否删除规则下的预警信息
     * 1、预警维度为逻辑仓下的所有物料时，逻辑仓为空，删除预警信息
     * 2、预警维度为指定物料时，物料信息为空，删除预警信息
     * @param ruleBO
     * @return
     */
    private boolean validateDeleteAlert(InventoryMonitorRuleBO ruleBO){
        List<InventoryMonitorRuleLineBO> lineBOS = monitorRuleLineDomainService.queryByMonitorRuleIds(Lists.newArrayList(ruleBO.getId()));
        return CollectionUtils.isEmpty(lineBOS);
    }


    /**
     * 预警维度：逻辑仓 计算预警信息
     * @param ruleBO
     * @param snapshotPOList
     * @return
     */
    private boolean alertByLogicalPlantMaterial(InventoryMonitorRuleBO ruleBO,
                                                List<InventoryMonitorRuleLineBO> lineBOS,
                                                List<InventorySnapshotBO> snapshotPOList){
        Map<String, InventoryMonitorRuleLineBO> map = lineBOS.stream()
                .collect(Collectors.toMap(InventoryMonitorRuleLineBO::getMonitorObject, e -> e, (a, b) -> a));
        Map<String, BigDecimal> alertKeyDeviateMap = Maps.newHashMap();
        List<InventorySnapshotBO> alertMaterialList = Lists.newArrayList();
        List<String> delAlertMaterialCodes = Lists.newArrayList();
        for (InventorySnapshotBO snapshotPO : snapshotPOList) {
            if (snapshotPO == null){
                continue;
            }
            InventoryMonitorRuleLineBO lineBO = map.get(String.valueOf(snapshotPO.getLogicalPlantId()));
            if (lineBO == null){
                continue;
            }
            BigDecimal deviate = getDeviateAndValidateNeedAlert(lineBO.getMonitorCeil(), lineBO.getMonitorFloor(), snapshotPO.getUnrestricted());
            if (null == deviate){
                delAlertMaterialCodes.add(snapshotPO.getMaterialCode());
                continue;
            }
            alertKeyDeviateMap.put(snapshotPO.getMaterialCode(), deviate);
            alertMaterialList.add(snapshotPO);
        }
        return createInventoryAlert(alertMaterialList, ruleBO, alertKeyDeviateMap, delAlertMaterialCodes);
    }

    /**
     * 预警维度：指定物料  计算预警信息
     * @param ruleBO
     * @param snapshotPOList
     * @return
     */
    private boolean alertByAssignMaterial(InventoryMonitorRuleBO ruleBO,
                                          List<InventoryMonitorRuleLineBO> lineBOS,
                                          List<InventorySnapshotBO> snapshotPOList){
        Map<String, InventoryMonitorRuleLineBO> map = lineBOS.stream()
                .collect(Collectors.toMap(InventoryMonitorRuleLineBO::getMonitorObject, e -> e, (a, b) -> a));
        Map<String, BigDecimal> alertKeyDeviateMap = Maps.newHashMap();
        //1、计算物料合计
        Map<String, List<InventorySnapshotBO>> listMap = snapshotPOList.stream()
                .filter(e -> Objects.nonNull(e) && Objects.nonNull(e.getMaterialCode()))
                .collect(Collectors.groupingBy(InventorySnapshotBO::getMaterialCode));

        Map<String, BigDecimal> unrestrictedTotalMap = Maps.newHashMap();
        List<String> delAlertMaterialCodes = Lists.newArrayList();
        for (Map.Entry<String, List<InventorySnapshotBO>> entry : listMap.entrySet()) {
            if (StringUtils.isBlank(entry.getKey()) || CollectionUtils.isEmpty(entry.getValue())) {
                continue;
            }
            //正品集合
            BigDecimal unrestrictedTotal = entry.getValue().stream()
                    .filter(e -> Objects.nonNull(e) && Objects.nonNull(e.getUnrestricted()))
                    .map(InventorySnapshotBO::getUnrestricted).reduce(BigDecimal.ZERO, BigDecimal::add);
            unrestrictedTotalMap.put(entry.getKey(), unrestrictedTotal);
        }
        //2、计算是否需要预警
        for (Map.Entry<String, BigDecimal> entry : unrestrictedTotalMap.entrySet()) {
            if (entry == null){
                continue;
            }
            InventoryMonitorRuleLineBO lineBO = map.get(entry.getKey());
            if (lineBO == null){
                continue;
            }
            BigDecimal deviate = getDeviateAndValidateNeedAlert(lineBO.getMonitorCeil(), lineBO.getMonitorFloor(), entry.getValue());
            if (deviate == null){
                delAlertMaterialCodes.add(entry.getKey());
                continue;
            }
            alertKeyDeviateMap.put(entry.getKey(), deviate);
        }
        //3、需要预警的物料
        List<InventorySnapshotBO> alertMaterialList = snapshotPOList.stream()
                .filter(e -> Objects.nonNull(alertKeyDeviateMap.get(e.getMaterialCode())))
                .collect(Collectors.toList());

        //4、保存预警日志信息
        return createInventoryAlert(alertMaterialList, ruleBO, alertKeyDeviateMap, delAlertMaterialCodes);
    }

    private List<InventorySnapshotBO> getInventorySnapshot(InventoryMonitorRuleBO ruleBO, List<InventoryMonitorRuleLineBO> lineBOS){
        MonitorInventorySnapshotPageBO reqBO = new MonitorInventorySnapshotPageBO();
        reqBO.setTenantId(ruleBO.getTenantId());
        reqBO.setPageNum(CommonConstant.NUM_ONE);
        reqBO.setPageSize(CommonConstant.NUM_1000);
        List<String> monitorObjectList = lineBOS.stream().map(InventoryMonitorRuleLineBO::getMonitorObject).collect(Collectors.toList());
        return snapshotDomainService.getSnapshotByMonitor(ruleBO.getTenantId(), monitorObjectList, ruleBO.getMonitorDimension());
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean createInventoryAlert(List<InventorySnapshotBO> alertMaterial, InventoryMonitorRuleBO ruleBO,
                                           Map<String, BigDecimal> alertKeyDeviateMap,
                                           List<String> delAlertMaterialCodes){

        if (!MapUtil.isEmpty(alertKeyDeviateMap)){
            //1、创建预警日志和记录
            boolean createAlertResult = alertDomainService.createAlertAndNotification(alertMaterial, ruleBO, alertKeyDeviateMap);
            if (!createAlertResult) {
                log.error("AbstractMonitorHandleChain.createInventoryAlert createAlert error. alertKeyDeviateMap={}", JSON.toJSONString(alertKeyDeviateMap));
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_ALERT_CREATE_FAILED);
            }
            MonitorMessageBO messageBO = MonitorMessageBO.builder().monitorRuleId(ruleBO.getId()).build();
            monitorMqProduce.sendMonitor(messageBO, InventoryTagEnum.INVENTORY_ALERT_CREATE);

            //发送库存提醒
            List<MonitorQuantityMessageBO> monitorQuantityMessageBOs = assembleMonitorQuantityMessage(alertMaterial);
            monitorQuantityMqProduce.sendQuantityMonitor(monitorQuantityMessageBOs, InventoryTagEnum.MONITOR_QUANTITY);
        }
        if (!CollectionUtils.isEmpty(delAlertMaterialCodes)){
            //查询具体的告警信息
            InventoryAlertBO alertBO = new InventoryAlertBO();
            alertBO.setMonitorRuleId(ruleBO.getId());
            alertBO.setTenantId(ruleBO.getTenantId());
            List<InventoryAlertBO> alertBOList = alertCoreService.queryByMaterialCodes(delAlertMaterialCodes, alertBO);
            List<Long> deleteAlertIds = Optional.ofNullable(alertBOList).orElse(Lists.newArrayList()).stream()
                    .filter(Objects::nonNull).map(InventoryAlertBO::getId).collect(Collectors.toList());
            //2、删除预警日志和记录
            boolean deleteAlertResult = alertDomainService.deleteAlertAndNotification(ruleBO, deleteAlertIds);
            if (!deleteAlertResult){
                log.error("AbstractMonitorHandleChain.createInventoryAlert deleteAlert error. deleteAlertIds={}", JSON.toJSONString(deleteAlertIds));
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_ALERT_DELETE_FAILED);
            }
        }
        return true;
    }

    /**
     * 包装库存提醒消息体
     * @param alertMaterial
     * @return
     */
    private List<MonitorQuantityMessageBO> assembleMonitorQuantityMessage(List<InventorySnapshotBO> alertMaterial) {
        List<MonitorQuantityMessageBO> result = new ArrayList<>();
        for (InventorySnapshotBO inventorySnapshot : alertMaterial){
            MonitorQuantityMessageBO messageBO = new MonitorQuantityMessageBO();
            messageBO.setQuantity(inventorySnapshot.getUnrestricted());
            messageBO.setTenantId(inventorySnapshot.getTenantId());
            messageBO.setMaterialCode(inventorySnapshot.getMaterialCode());
            result.add(messageBO);
        }
        return result;
    }


    /**
     * 对比上下限是否需要预警并返回偏差数量
     * @param ceil      上限
     * @param floor     下限
     * @param target    目标值
     * @return
     */
    protected BigDecimal getDeviateAndValidateNeedAlert(BigDecimal ceil, BigDecimal floor, BigDecimal target){
        if (null == target){
            return null;
        }
        if (null != ceil && target.compareTo(ceil) > 0){
            return target.subtract(ceil);
        }
        if (null != floor && floor.compareTo(target) > 0){
            return target.subtract(floor);
        }
        return null;
    }
}
