package com.inventory.middle.domain.service.mq;

import com.inventory.middle.domain.model.enums.InventoryTagEnum;

/**
 * 库存MQ生产者接口（Domain层只定义接口，具体实现在 Infra 层）
 */
public interface InventoryMqProducer {
    void sendInventoryChange(Object message, InventoryTagEnum tagEnum);
    void sendMonitor(Object message, InventoryTagEnum tagEnum);
    void sendAnnualInspection(Object message, InventoryTagEnum tagEnum);
    void sendQuantityMonitor(Object message, InventoryTagEnum tagEnum);
}
