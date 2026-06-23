package com.inventory.middle.domain.service.impl;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.converter.InventoryMonitorRuleBizConverter;
import com.inventory.middle.domain.service.InventoryMonitorRuleDomainService;
import com.inventory.middle.domain.service.InventoryMonitorRuleLineDomainService;
import com.inventory.middle.domain.service.validator.InventoryMonitorRuleValidator;
import com.inventory.middle.client.dto.monitory.FailedCreateMonitorRuleLineDTO;
import com.inventory.middle.client.dto.monitory.InventoryMonitorRuleLineResponse;
import com.inventory.middle.client.enums.BaseEnableStatusEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleDimensionEnum;
import com.inventory.middle.domain.model.enums.InventoryAlertActionEnum;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRulePageQuery;
import com.inventory.middle.domain.model.bo.monitor.UpdateMonitorRuleLineReq;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import com.inventory.middle.domain.service.InventoryMonitorRuleCoreService;
import com.inventory.middle.domain.service.InventoryMonitorRuleLineCoreService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Service
@Slf4j
public class InventoryMonitorRuleDomainServiceImpl implements InventoryMonitorRuleDomainService {

    @Resource
    private InventoryMonitorRuleCoreService monitorRuleCoreService;
    @Resource
    private InventoryMonitorRuleLineCoreService monitorRuleLineCoreService;
    @Resource
    private InventoryMqProducer monitorMqProduce;
    @Resource
    private InventoryMonitorRuleValidator ruleValidator;
    @Resource
    private InventoryMonitorRuleLineDomainService ruleLineDomainService;

    @Override
    public SingleResponse<Long> create(InventoryMonitorRuleBO ruleBO) {
        Long monitorRuleId = monitorRuleCoreService.create(ruleBO);
        if (null == monitorRuleId) {
            throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_CREATE_FAILED);
        }
        return SingleResponse.of(monitorRuleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<Boolean> delete(InventoryMonitorRuleBO monitorRuleBO) {
        boolean ruleUpdateResult = monitorRuleCoreService.update(monitorRuleBO);
        if (!ruleUpdateResult) {
            log.error("InventoryMonitorRuleDomainServiceImpl.delete failed. monitorRuleBO={}", JSON.toJSONString(monitorRuleBO));
            throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_DELETE_FAILED);
        }
        List<InventoryMonitorRuleLineBO> ruleLineBOS = monitorRuleLineCoreService.queryByMonitorRuleIds(Lists.newArrayList(monitorRuleBO.getId()));
        if (!CollectionUtils.isEmpty(ruleLineBOS)) {
            InventoryMonitorRuleLineBO lineBO = InventoryMonitorRuleBizConverter.convertToDeleteRuleLineBo(monitorRuleBO);
            boolean ruleLineUpdateResult = monitorRuleLineCoreService.updateByMonitorRuleId(lineBO);
            if (!ruleLineUpdateResult) {
                log.error("InventoryMonitorRuleDomainServiceImpl.delete ruleLine failed. lineBO={}", JSON.toJSONString(lineBO));
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_DELETE_FAILED);
            }
        }
        return SingleResponse.of(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<Boolean> updateRuleAndLine(InventoryMonitorRuleBO ruleRequest, UpdateMonitorRuleLineReq lineRequest) {
        if (needDeleteAllRuleLine(ruleRequest)) {
            List<InventoryMonitorRuleLineBO> lineBOS = ruleLineDomainService.queryByMonitorRuleIds(Lists.newArrayList(ruleRequest.getId()));
            List<Long> ruleLineIds = Optional.ofNullable(lineBOS).orElse(Lists.newArrayList())
                    .stream().filter(Objects::nonNull)
                    .map(InventoryMonitorRuleLineBO::getId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(lineRequest.getDeleteList())) {
                lineRequest.setDeleteList(ruleLineIds);
            } else {
                lineRequest.getDeleteList().addAll(ruleLineIds);
            }
        }
        SingleResponse<Boolean> ruleUpdateResult = update(ruleRequest);
        boolean lineUpdateResult = ruleLineDomainService.updateRuleLine(lineRequest);
        if (Objects.nonNull(ruleUpdateResult) && ruleUpdateResult.isSuccess() && lineUpdateResult) {
            return SingleResponse.of(true);
        }
        throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_UPDATE_FAILED);
    }

    private boolean needDeleteAllRuleLine(InventoryMonitorRuleBO ruleRequest) {
        InventoryMonitorRuleBO ruleBO = monitorRuleCoreService.load(ruleRequest.getId());
        if (!StringUtils.equals(ruleRequest.getMonitorDimension(), ruleBO.getMonitorDimension())) { return true; }
        if (!StringUtils.equals(ruleRequest.getMonitorType(), ruleBO.getMonitorType())) { return true; }
        return false;
    }

    @Override
    public SingleResponse<Boolean> update(InventoryMonitorRuleBO ruleBO) {
        validateMonitorUpdate(ruleBO);
        InventoryMonitorRuleBO dbRule = monitorRuleCoreService.load(ruleBO.getId());
        boolean changedDimension = !StringUtils.equals(dbRule.getMonitorDimension(), ruleBO.getMonitorDimension());
        MonitorMessageBO messageBO = MonitorMessageBO.builder()
                .monitorRuleId(ruleBO.getId()).changedDimension(changedDimension)
                .beanName(InventoryAlertActionEnum.MONITOR_RULE_UPDATE.getBeanName()).build();
        boolean ruleUpdateResult = monitorRuleCoreService.update(ruleBO);
        if (!ruleUpdateResult) {
            throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_UPDATE_FAILED);
        }
        monitorMqProduce.sendMonitor(messageBO, InventoryTagEnum.MONITOR_RULE_UPDATE);
        return SingleResponse.of(true);
    }

    private void validateMonitorUpdate(InventoryMonitorRuleBO updateRequest) {
        if (BaseEnableStatusEnum.DISABLE.name().equals(updateRequest.getMonitorEnableStatus())) { return; }
        InventoryMonitorRuleBO ruleBO = queryById(updateRequest.getId());
        if (null == ruleBO) { throw new BusinessException(ResponseCodeEnum.MONITOR_NOT_EXISTS); }
        List<InventoryMonitorRuleLineBO> lineBOList = ruleLineDomainService.queryByMonitorRuleIds(Lists.newArrayList(ruleBO.getId()));
        if (CollectionUtils.isEmpty(lineBOList)) { throw new BusinessException(ResponseCodeEnum.MONITOR_ENABLE_FAILED_RULE_LINE); }
        boolean changedEnableStatus = !StringUtils.equals(updateRequest.getMonitorEnableStatus(), ruleBO.getMonitorEnableStatus());
        boolean changeDMonitorType = !StringUtils.equals(updateRequest.getMonitorType(), ruleBO.getMonitorType());
        if (changedEnableStatus || changeDMonitorType) {
            ArrayList<InventoryMonitorRuleLineResponse> list = InventoryMonitorRuleBizConverter.convertRuleLineBoToResponses(lineBOList);
            List<FailedCreateMonitorRuleLineDTO> failedList = ruleLineDomainService.validateCreateMaterial(list, changedEnableStatus);
            if (!CollectionUtils.isEmpty(failedList)) {
                throw new BusinessException(ResponseCodeEnum.MONITOR_RULE_UPDATE_FAILED_WITH_REASON.getCode(),
                        String.format(ResponseCodeEnum.MONITOR_RULE_UPDATE_FAILED_WITH_REASON.getDesc(),
                                MonitorRuleDimensionEnum.getDescByName(updateRequest.getMonitorDimension())));
            }
        }
    }

    @Override
    public InventoryMonitorRuleBO queryById(Long id) {
        return monitorRuleCoreService.load(id);
    }

    @Override
    public PageResponse<InventoryMonitorRuleBO> pageList(InventoryMonitorRulePageQuery pageQuery) {
        return monitorRuleCoreService.pageList(pageQuery);
    }

    @Override
    public PageResponse<InventoryMonitorRuleBO> pageListFromMaxId(InventoryMonitorRulePageQuery pageQuery, Long ruleId) {
        return monitorRuleCoreService.pageListFromMaxId(pageQuery, ruleId);
    }

    @Override
    public List<InventoryMonitorRuleBO> queryByMonitorObjects(String tenantId, List<String> monitorObjects, String monitorDimension) {
        String enableStatus = BaseEnableStatusEnum.ENABLE.name();
        return monitorRuleCoreService.queryByMonitorObjects(monitorObjects, monitorDimension, tenantId, enableStatus);
    }
}
