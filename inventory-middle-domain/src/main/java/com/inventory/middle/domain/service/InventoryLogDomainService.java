package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.log.InventoryLogBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogPageQueryBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogQueryBO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.List;

/** 库存日志业务服务接口 */
public interface InventoryLogDomainService {
    SingleResponse<Boolean> createBatchAsync(List<InventoryLogBO> logBOs);
    List<InventoryLogBO> list(InventoryLogQueryBO queryBO);
    PageResponse<InventoryLogBO> pageList(InventoryLogPageQueryBO pageQueryBO);
}
