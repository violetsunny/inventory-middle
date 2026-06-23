package com.inventory.middle.domain.service.converter;

import com.inventory.middle.client.enums.BaseStatusEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleSendModeEnum;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryAlertBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryAlertNotificationBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** InventoryAlert 转换器（迁移自 biz-service，适配 domain BO） */
public class InventoryAlertBizConverter {

    public static InventoryAlertBO buildDeleteAlertBo(InventoryMonitorRuleBO ruleBO) {
        InventoryAlertBO alertBO = new InventoryAlertBO();
        alertBO.setMonitorRuleId(ruleBO.getId());
        alertBO.setUpdateTime(new Date());
        alertBO.setUpdatorId(ruleBO.getUpdatorId());
        alertBO.setDeleted(BaseStatusEnum.YES.getCode());
        return alertBO;
    }

    public static InventoryAlertNotificationBO buildDeleteNotificationBO(InventoryMonitorRuleBO ruleBO) {
        InventoryAlertNotificationBO notificationBO = new InventoryAlertNotificationBO();
        notificationBO.setUpdateTime(new Date());
        notificationBO.setUpdatorId(ruleBO.getUpdatorId());
        notificationBO.setDeleted(BaseStatusEnum.YES.getCode());
        return notificationBO;
    }

    public static List<InventoryAlertNotificationBO> convertToCreateNotificationBos(
            List<InventoryAlertBO> alertBOS, InventoryMonitorRuleBO ruleBO, Map<Long, LogicalPlantBO> logicalPlantMap) {
        if (CollectionUtils.isEmpty(alertBOS)) { return Lists.newArrayList(); }
        List<InventoryAlertNotificationBO> notificationBOS = Lists.newArrayList();
        for (InventoryAlertBO alertBO : alertBOS) {
            if (alertBO == null) { continue; }
            InventoryAlertNotificationBO notificationBO = convertToCreateNotificationBO(alertBO, ruleBO);
            LogicalPlantBO logicalPlantBO = logicalPlantMap.get(alertBO.getLogicalPlantId());
            String logicalPlantName = Objects.isNull(logicalPlantBO) ? StringUtils.EMPTY : logicalPlantBO.getLogicalPlantName();
            DecimalFormat df = new DecimalFormat("######.######");
            String decimal = Objects.isNull(alertBO.getDeviate()) ? BigDecimal.ZERO.toString() : df.format(alertBO.getDeviate());
            String content = String.format(CommonConstant.ALERT_CONTENT, logicalPlantName, alertBO.getMaterialCode(), alertBO.getAction(), decimal);
            notificationBO.setContent(content);
            notificationBOS.add(notificationBO);
        }
        return notificationBOS;
    }

    public static InventoryAlertNotificationBO convertToCreateNotificationBO(InventoryAlertBO alertBO, InventoryMonitorRuleBO ruleBO) {
        InventoryAlertNotificationBO bo = new InventoryAlertNotificationBO();
        bo.setAlertId(alertBO.getId());
        bo.setNotificationMode(ruleBO.getMonitorSendMode());
        bo.setNotificationAddress(ruleBO.getMonitorSendAddress());
        bo.setUrl(StringUtils.EMPTY);
        bo.setStatus(BaseStatusEnum.NO.getCode());
        bo.setCreateTime(new Date());
        bo.setUpdateTime(bo.getCreateTime());
        bo.setCreatorId(alertBO.getCreatorId());
        bo.setUpdatorId(bo.getCreatorId());
        bo.setDeleted(BaseStatusEnum.NO.getCode());
        return bo;
    }

    public static List<InventoryAlertBO> convertSnapshotPosToCreateAlertBos(
            List<InventorySnapshotBO> snapshotBOs, InventoryMonitorRuleBO ruleBO, Map<String, BigDecimal> alertKeyDeviateMap) {
        List<InventoryAlertBO> list = Lists.newArrayList();
        for (InventorySnapshotBO snapshotBO : snapshotBOs) {
            InventoryAlertBO alertBO = convertSnapshotBoToCreateAlertBo(snapshotBO, ruleBO);
            BigDecimal deviate = alertKeyDeviateMap.get(snapshotBO.getMaterialCode());
            alertBO.setDeviate(deviate);
            alertBO.setAction(deviate != null && deviate.compareTo(BigDecimal.ZERO) > 0 ? "CEIL" : "FLOOR");
            list.add(alertBO);
        }
        return list;
    }

    public static InventoryAlertBO convertSnapshotBoToCreateAlertBo(InventorySnapshotBO snapshotBO, InventoryMonitorRuleBO ruleBO) {
        if (snapshotBO == null) { return null; }
        InventoryAlertBO alertBO = new InventoryAlertBO();
        alertBO.setTenantId(ruleBO.getTenantId());
        alertBO.setMonitorRuleId(ruleBO.getId());
        alertBO.setMaterialCode(snapshotBO.getMaterialCode());
        alertBO.setLogicalPlantId(snapshotBO.getLogicalPlantId());
        alertBO.setCreateTime(new Date());
        alertBO.setCreatorId(ruleBO.getCreatorId());
        alertBO.setUpdateTime(alertBO.getCreateTime());
        alertBO.setUpdatorId(ruleBO.getUpdatorId());
        alertBO.setDeleted(BaseStatusEnum.NO.getCode());
        alertBO.setAlertDate(new Date());
        return alertBO;
    }

    public static List<Map<String, List<InventoryAlertNotificationBO>>> convertEmailContentList(List<InventoryAlertNotificationBO> notificationBOS) {
        Map<String, List<InventoryAlertNotificationBO>> map = convertEmailContentMap(notificationBOS);
        return splitMap(map, CommonConstant.EMAIL_PARTITION_SIZE);
    }

    public static Map<String, List<InventoryAlertNotificationBO>> convertEmailContentMap(List<InventoryAlertNotificationBO> notificationBOS) {
        if (CollectionUtils.isEmpty(notificationBOS)) { return Maps.newHashMap(); }
        return notificationBOS.stream()
                .filter(e -> Objects.nonNull(e) && isValidEmail(e.getNotificationAddress()))
                .sorted(Comparator.comparing(InventoryAlertNotificationBO::getContent))
                .collect(Collectors.groupingBy(InventoryAlertNotificationBO::getNotificationAddress));
    }

    private static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) { return false; }
        return Pattern.matches(CommonConstant.EMAIL_REGULAR, email);
    }

    public static <K, V> List<Map<K, V>> splitMap(Map<K, V> map, int pageSize) {
        if (map == null || map.isEmpty()) { return Collections.emptyList(); }
        pageSize = pageSize == 0 ? CommonConstant.PARTITION_SIZE : pageSize;
        List<Map<K, V>> newList = Lists.newArrayList();
        int j = 0;
        for (K k : map.keySet()) {
            if (j % pageSize == 0) { newList.add(Maps.newHashMap()); }
            newList.get(newList.size() - 1).put(k, map.get(k));
            j++;
        }
        return newList;
    }
}
