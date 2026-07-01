package com.inventory.middle.domain.service.impl;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.InventoryLogDomainService;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.log.InventoryLogBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogPageQueryBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogQueryBO;
import com.inventory.middle.domain.service.InventoryLogCoreService;
import com.inventory.middle.domain.service.validator.InventoryLogValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongguo.tao
 * @date 2021-12-14 16:18:33
 */
@Service
@Slf4j
public class InventoryLogDomainServiceImpl implements InventoryLogDomainService {

    @Resource
    private InventoryLogCoreService inventoryLogCoreService;
    @Resource
    private InventoryLogValidator logValidator;

    @Override
    @Async("inventoryCenterExecutor")
    public SingleResponse<Boolean> createBatchAsync(List<InventoryLogBO> logBOs) {
        SingleResponse<Boolean> response = SingleResponse.of(Boolean.TRUE);
        try {
            logValidator.validateLogBos(logBOs);
            boolean result = inventoryLogCoreService.insertBatch(logBOs);
            if (!result) {
                log.warn("InventoryLogDomainServiceImpl.createBatchAsync logBOs={}", JSON.toJSONString(logBOs));
                response = SingleResponse.buildFailure(ResponseCodeEnum.FAILED.getCode(), ResponseCodeEnum.FAILED.getDesc());
            }
        } catch (BusinessException e) {
            log.error("InventoryLogDomainServiceImpl.createBatchAsync business error. logBOs={}", JSON.toJSONString(logBOs), e);
            response = SingleResponse.buildFailure(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.error("InventoryLogDomainServiceImpl.createBatchAsync error. logBOs={}", JSON.toJSONString(logBOs), e);
            response = SingleResponse.buildFailure(ResponseCodeEnum.SYSTEM_ERROR.getCode(), ResponseCodeEnum.SYSTEM_ERROR.getDesc());
        }
        return response;
    }

    @Override
    public List<InventoryLogBO> list(InventoryLogQueryBO queryBO) {
        logValidator.validateLogQueryBo(queryBO);
        return inventoryLogCoreService.list(queryBO);
    }

    @Override
    public PageResponse<InventoryLogBO> pageList(InventoryLogPageQueryBO pageQueryBO) {
        logValidator.validateLogQueryBo(pageQueryBO);
        return inventoryLogCoreService.pageList(pageQueryBO);
    }
}
