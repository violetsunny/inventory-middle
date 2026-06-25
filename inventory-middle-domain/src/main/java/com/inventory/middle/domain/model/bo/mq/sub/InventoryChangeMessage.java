package com.inventory.middle.domain.model.bo.mq.sub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

/** 库存变化 MQ 消息（与 infra.produce.model.InventoryChangeMessage 字段对齐） */
@Data @AllArgsConstructor @NoArgsConstructor(force = true) @Builder
public class InventoryChangeMessage implements Serializable {
    private String materialCode;
    private String logicalPlantNo;
    /** 1-入库 2-出库 */
    private Integer inventoryCategory;
    /** 移动数量 */
    private BigDecimal adjustQuantity;
    /** 不含税单价 */
    private BigDecimal price;
    private String currency;
    private BigDecimal exchangeRate;
    private Boolean calMap;
    private String operator;
    private String tenantId;
    private String mapCode;
    private String mapSubCode;
    private String version;
}
