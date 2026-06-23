package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.converter.InventoryMonitorRuleBizConverter;
import com.inventory.middle.domain.service.InventoryMonitorRuleLineDomainService;
import com.inventory.middle.domain.service.InventorySnapshotDomainService;
import com.inventory.middle.client.dto.monitory.FailedCreateMonitorRuleLineDTO;
import com.inventory.middle.client.dto.monitory.InventoryMonitorRuleLineInfoDTO;
import com.inventory.middle.client.enums.BaseEnableStatusEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleDimensionEnum;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.common.util.CommonHelper;
import com.inventory.middle.domain.model.enums.InventoryAlertActionEnum;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.inventorysnapshot.MonitorInventorySnapshotPageBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLinePageQuery;
import com.inventory.middle.domain.model.bo.monitor.UpdateMonitorRuleLineReq;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import com.inventory.middle.domain.service.InventoryMonitorRuleCoreService;
import com.inventory.middle.domain.service.InventoryMonitorRuleLineCoreService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageResponse;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Service
@Slf4j
public class InventoryMonitorRuleLineDomainServiceImpl implements InventoryMonitorRuleLineDomainService {

    @Resource
    private InventoryMonitorRuleLineCoreService ruleLineCoreService;
    @Resource
    private InventoryMqProducer monitorMqProduce;
    @Resource
    private InventoryMonitorRuleCoreService ruleCoreService;
    @Resource
    private InventorySnapshotDomainService snapshotDomainService;
    @Resource
    private SnowflakeGenerator snowflakeGenerator;

    @Override
    public List<InventoryMonitorRuleLineBO> queryByMonitorRuleIds(List<Long> monitorRuleIds) {
        return ruleLineCoreService.queryByMonitorRuleIds(monitorRuleIds);
    }

    @Override
    public PageResponse<InventoryMonitorRuleLineBO> pageRuleLineList(InventoryMonitorRuleLinePageQuery pageQuery) {
        return ruleLineCoreService.pageRuleLineList(pageQuery);
    }

    @Override
    public List<InventoryMonitorRuleLineBO> queryByIds(List<Long> ruleLineIds) {
        return ruleLineCoreService.queryByIds(ruleLineIds);
    }

    @Override
    public boolean batchCreate(List<InventoryMonitorRuleLineBO> list) {
        boolean result = ruleLineCoreService.batchCreate(list);
        if (result) {
            Long monitorRuleId = list.get(CommonConstant.NUM_ZERO).getMonitorRuleId();
            List<Long> ruleLineIds = list.stream().map(InventoryMonitorRuleLineBO::getId).collect(Collectors.toList());
            sendMq(InventoryTagEnum.MONITOR_RULE_LINE_CREATE, monitorRuleId, ruleLineIds, null);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRuleLine(UpdateMonitorRuleLineReq request) {
        validateUpdateLineMaterial(request);
        InventoryMonitorRuleLineBO ruleLineBO = InventoryMonitorRuleBizConverter.convertToDeleteRuleBo(request);
        List<Long> ruleLineIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(request.getCreateList())) {
            request.getCreateList().stream().filter(Objects::nonNull).forEach(e -> e.setId(snowflakeGenerator.next()));
            boolean createResult = ruleLineCoreService.batchCreate(request.getCreateList());
            if (!createResult) {
                log.error("InventoryMonitorRuleLineDomainServiceImpl.batchCreate failed. createList={}", JSON.toJSONString(request.getCreateList()));
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_LINE_UPDATE_FAILED);
            }
            List<Long> createIds = request.getCreateList().stream().map(InventoryMonitorRuleLineBO::getId).collect(Collectors.toList());
            ruleLineIds.addAll(createIds);
        }
        if (!CollectionUtils.isEmpty(request.getUpdateList())) {
            boolean updateResult = ruleLineCoreService.updateBatch(request.getUpdateList());
            if (!updateResult) {
                log.error("InventoryMonitorRuleLineDomainServiceImpl.updateBatch failed. updateList={}", JSON.toJSONString(request.getUpdateList()));
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_LINE_UPDATE_FAILED);
            }
            List<Long> updateIds = request.getUpdateList().stream().map(InventoryMonitorRuleLineBO::getId).collect(Collectors.toList());
            ruleLineIds.addAll(updateIds);
        }
        if (!CollectionUtils.isEmpty(request.getDeleteList())) {
            boolean deleteResult = ruleLineCoreService.updateByIds(ruleLineBO, request.getDeleteList());
            if (!deleteResult) {
                log.error("InventoryMonitorRuleLineDomainServiceImpl.updateByIds failed. ruleLineBO={},ids={}",
                        JSON.toJSONString(request.getUpdateList()), JSON.toJSONString(request.getDeleteList()));
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_LINE_UPDATE_FAILED);
            }
            if (CollectionUtils.isEmpty(request.getCreateList()) && CollectionUtils.isEmpty(request.getUpdateList())) {
                List<InventoryMonitorRuleLineBO> lineBOS = ruleLineCoreService.queryByMonitorRuleIds(Lists.newArrayList(request.getMonitorRuleId()));
                if (CollectionUtils.isEmpty(lineBOS)) {
                    boolean updateResult = updateMonitorRuleDisable(request.getMonitorRuleId(), request.getOperatorId());
                    if (!updateResult) {
                        log.error("InventoryMonitorRuleLineDomainServiceImpl.updateMonitorRule failed. ruleLineBO={},ids={}",
                                JSON.toJSONString(request.getUpdateList()), JSON.toJSONString(request.getDeleteList()));
                        throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_UPDATE_FAILED);
                    }
                }
            }
        }
        sendMq(InventoryTagEnum.MONITOR_RULE_LINE_UPDATE, request.getMonitorRuleId(), ruleLineIds, request.getDeleteList());
        return true;
    }

    public void validateUpdateLineMaterial(UpdateMonitorRuleLineReq request) {
        if (!CollectionUtils.isEmpty(request.getCreateList())) {
            List<InventoryMonitorRuleLineInfoDTO> createInfoDtos = InventoryMonitorRuleBizConverter.convertBoToInfoDtos(request.getCreateList());
            List<FailedCreateMonitorRuleLineDTO> failedList = validateCreateMaterial(createInfoDtos, null);
            if (!CollectionUtils.isEmpty(failedList)) {
                throw new BusinessException(ResponseCodeEnum.MONITOR_MATERIAL_LOGICAL_PLANT_EXISTS);
            }
        }
        if (!CollectionUtils.isEmpty(request.getUpdateList())) {
            List<Long> ruleLineIds = request.getUpdateList().stream().map(InventoryMonitorRuleLineBO::getId).collect(Collectors.toList());
            List<InventoryMonitorRuleLineBO> ruleLineBOS = queryByIds(ruleLineIds);
            List<InventoryMonitorRuleLineInfoDTO> list;
            if (CollectionUtils.isEmpty(ruleLineBOS)) {
                list = InventoryMonitorRuleBizConverter.convertBoToInfoDtos(request.getUpdateList());
            } else {
                Map<Long, String> map = ruleLineBOS.stream()
                        .collect(Collectors.toMap(InventoryMonitorRuleLineBO::getId, InventoryMonitorRuleLineBO::getMonitorObject));
                list = request.getUpdateList().stream()
                        .filter(e -> !StringUtils.equals(e.getMonitorObject(), map.get(e.getId())))
                        .map(InventoryMonitorRuleBizConverter::convertBoToInfoDTO)
                        .collect(Collectors.toList());
            }
            List<FailedCreateMonitorRuleLineDTO> updateFailedList = validateCreateMaterial(list, null);
            if (!CollectionUtils.isEmpty(updateFailedList)) {
                throw new BusinessException(ResponseCodeEnum.MONITOR_MATERIAL_LOGICAL_PLANT_EXISTS);
            }
        }
    }

    private boolean updateMonitorRuleDisable(Long monitorRuleId, String operatorId) {
        InventoryMonitorRuleBO ruleBO = ruleCoreService.load(monitorRuleId);
        if (ruleBO == null || StringUtils.equals(BaseEnableStatusEnum.DISABLE.name(), ruleBO.getMonitorEnableStatus())) {
            return true;
        }
        ruleBO.setMonitorEnableStatus(BaseEnableStatusEnum.DISABLE.name());
        ruleBO.setUpdateTime(new Date());
        ruleBO.setUpdatorId(operatorId);
        ruleBO.setCreateTime(null);
        ruleBO.setCreatorId(null);
        ruleBO.setDeleted(null);
        return ruleCoreService.update(ruleBO);
    }

    @Override
    public List<InventoryMonitorRuleLineBO> queryByMonitorObjects(String tenantId, String monitorType, List<String> monitorObjects, String monitorDimension) {
        String enableStatus = BaseEnableStatusEnum.ENABLE.name();
        return ruleLineCoreService.queryByMonitorObjects(monitorObjects, monitorType, monitorDimension, tenantId, enableStatus);
    }

    private void sendMq(InventoryTagEnum tag, Long monitorRuleId, List<Long> ruleLineIds, List<Long> deleteRuleLineIds) {
        MonitorMessageBO messageBO = MonitorMessageBO.builder()
                .monitorRuleId(monitorRuleId)
                .beanName(InventoryAlertActionEnum.MONITOR_RULE_LINE.getBeanName())
                .ruleLineIds(ruleLineIds)
                .deletedRuleLineIds(deleteRuleLineIds)
                .build();
        log.info("InventoryMonitorRuleLineDomainServiceImpl.send tag={},message={}", tag, JSON.toJSONString(messageBO));
        monitorMqProduce.sendMonitor(messageBO, tag);
    }

    @Override
    public <T extends InventoryMonitorRuleLineInfoDTO> List<FailedCreateMonitorRuleLineDTO> validateCreateMaterial(List<T> infoDTOS, Boolean enableStatus) {
        if (CollectionUtils.isEmpty(infoDTOS)) { return Lists.newArrayList(); }
        Long monitorRuleId = infoDTOS.get(CommonConstant.NUM_ZERO).getMonitorRuleId();
        InventoryMonitorRuleBO ruleBO = ruleCoreService.load(monitorRuleId);
        if (null == ruleBO) { throw new BusinessException(ResponseCodeEnum.MONITOR_NOT_EXISTS); }
        if (!BooleanUtils.toBoolean(enableStatus) && StringUtils.equals(BaseEnableStatusEnum.DISABLE.name(), ruleBO.getMonitorEnableStatus())) {
            return Lists.newArrayList();
        }
        String dimension = infoDTOS.get(CommonConstant.NUM_ZERO).getMonitorDimension();
        if (!StringUtils.equals(dimension, ruleBO.getMonitorDimension())) { return Lists.newArrayList(); }
        List<InventoryMonitorRuleLineInfoDTO> infoDTOList = (List<InventoryMonitorRuleLineInfoDTO>) infoDTOS;
        List<FailedCreateMonitorRuleLineDTO> failedList = Lists.newArrayList();
        List<String> monitorObjects = infoDTOS.stream().map(InventoryMonitorRuleLineInfoDTO::getMonitorObject).collect(Collectors.toList());
        List<InventoryMonitorRuleLineBO> lineBOS = queryByMonitorObjects(ruleBO.getTenantId(), ruleBO.getMonitorType(), monitorObjects, dimension);
        if (!CollectionUtils.isEmpty(lineBOS)) {
            List<String> existsList = lineBOS.stream().map(InventoryMonitorRuleLineBO::getMonitorObject).distinct().collect(Collectors.toList());
            Map<Boolean, List<InventoryMonitorRuleLineInfoDTO>> listMap = infoDTOS.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(e -> existsList.contains(e.getMonitorObject())));
            List<FailedCreateMonitorRuleLineDTO> failedDtos = InventoryMonitorRuleBizConverter
                    .buildFailedDtos(listMap.get(true), ResponseCodeEnum.MONITOR_MATERIAL_EXISTS.getDesc());
            failedList.addAll(failedDtos);
            infoDTOList = listMap.get(false);
            if (!CollectionUtils.isEmpty(infoDTOList)) {
                monitorObjects = infoDTOList.stream().map(InventoryMonitorRuleLineInfoDTO::getMonitorObject).collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isEmpty(infoDTOList)) { return failedList; }
        List<InventorySnapshotBO> snapshotBOs = snapshotDomainService.getSnapshotByMonitor(ruleBO.getTenantId(), monitorObjects, dimension);
        if (CollectionUtils.isEmpty(snapshotBOs)) { return failedList; }
        List<InventoryMonitorRuleLineBO> ruleLineBOS = getMonitorRuleLineBySnapshots(snapshotBOs, ruleBO);
        ruleLineBOS = Optional.ofNullable(ruleLineBOS).orElse(Lists.newArrayList()).stream()
                .filter(e -> Objects.nonNull(e) && !Objects.equals(ruleBO.getId(), e.getMonitorRuleId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ruleLineBOS)) { return failedList; }
        List<String> materialObjects = ruleLineBOS.stream().map(InventoryMonitorRuleLineBO::getMonitorObject).distinct().collect(Collectors.toList());
        List<InventoryMonitorRuleLineInfoDTO> lastList = infoDTOList.stream()
                .filter(e -> materialObjects.contains(e.getMonitorObject())).collect(Collectors.toList());
        List<FailedCreateMonitorRuleLineDTO> list = InventoryMonitorRuleBizConverter
                .buildFailedDtos(lastList, ResponseCodeEnum.MONITOR_MATERIAL_LOGICAL_PLANT_EXISTS.getDesc());
        failedList.addAll(list);
        return failedList;
    }

    private List<InventoryMonitorRuleLineBO> getMonitorRuleLineBySnapshots(List<InventorySnapshotBO> snapshotBOs, InventoryMonitorRuleBO ruleBO) {
        List<String> logicalPlantIds = snapshotBOs.stream().filter(e -> Objects.nonNull(e.getLogicalPlantId()))
                .map(e -> String.valueOf(e.getLogicalPlantId())).distinct().collect(Collectors.toList());
        List<InventoryMonitorRuleLineBO> listByLogicalPlant = queryByMonitorObjects(ruleBO.getTenantId(),
                ruleBO.getMonitorType(), logicalPlantIds, MonitorRuleDimensionEnum.LOGICAL_PLANT_MATERIAL.name());
        List<String> materialCodes = snapshotBOs.stream().filter(e -> Objects.nonNull(e.getLogicalPlantId()))
                .map(InventorySnapshotBO::getMaterialCode).distinct().collect(Collectors.toList());
        List<InventoryMonitorRuleLineBO> listByMaterial = queryByMonitorObjects(ruleBO.getTenantId(),
                ruleBO.getMonitorType(), materialCodes, MonitorRuleDimensionEnum.ASSIGN_MATERIAL.name());
        if (CollectionUtils.isEmpty(listByMaterial)) { return listByLogicalPlant; }
        if (CollectionUtils.isEmpty(listByLogicalPlant)) { return listByMaterial; }
        List<InventoryMonitorRuleLineBO> ruleLineBOS = CommonHelper.diff(listByMaterial, listByLogicalPlant);
        if (!CollectionUtils.isEmpty(ruleLineBOS)) { listByLogicalPlant.addAll(ruleLineBOS); }
        return listByLogicalPlant;
    }
}