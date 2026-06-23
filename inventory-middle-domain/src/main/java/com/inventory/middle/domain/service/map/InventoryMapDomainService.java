package com.inventory.middle.domain.service.map;

import com.inventory.middle.domain.service.map.bo.InventoryMapBO;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;

/**
 * 移动平均价领域服务接口
 * 迁移自: com.enn.inventory.center.ext.biz.service.InventoryMapDomainService
 */
public interface InventoryMapDomainService {
    /** 计算并更新移动平均价 */
    void cal(InventoryChangeMessage message) throws Exception;
    /** 查询移动平均价 */
    InventoryMapBO queryInventoryMap(String skuCode, String logicalPlantNo, String tenantId);
}
