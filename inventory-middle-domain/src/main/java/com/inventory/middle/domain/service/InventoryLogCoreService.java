package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.log.InventoryLogBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogPageQueryBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogQueryBO;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

/**
 * @description 库存日志
 * @author dongguo.tao
 * @date 2021-12-14 15:21:35
 */
public interface InventoryLogCoreService {

    /**
     * 批量插入
     * @param logBOs
     * @return
     */
    boolean insertBatch(List<InventoryLogBO> logBOs);


    /**
     * 根据条件查询
     * @param queryBO
     * @return
     */
    List<InventoryLogBO> list(InventoryLogQueryBO queryBO);

    /**
     * 分页查询
     * @param pageQueryBO
     * @return
     */
    PageResponse<InventoryLogBO> pageList(InventoryLogPageQueryBO pageQueryBO);


}
