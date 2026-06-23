package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.log.InventoryLogCreateRequest;
import com.inventory.middle.client.dto.log.InventoryLogDTO;
import com.inventory.middle.client.dto.log.InventoryLogPageRequest;
// RDFA import removed;
// RDFA import removed;

import java.util.List;

/**
 * 库存日志
 * @author dongguo.tao
 * @date 2021-12-14 15:21:35
 */
public interface InventoryLogService {

    /**
     * 批量创建日志
     * @param requestList
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> createBatchAsync(List<InventoryLogCreateRequest> requestList);


    /**
     * 分页查询日志
     * @param pageRequest
     * @return
     */
    top.kdla.framework.dto.PageResponse<InventoryLogDTO> pageList(InventoryLogPageRequest pageRequest);


}
