package com.inventory.middle.domain.model.bo.mq.sub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

/** 库存变化 MQ 消息 */
@Data @AllArgsConstructor @NoArgsConstructor(force = true) @Builder
public class InventoryChangeMessage implements Serializable {
    private String materialCode;
    private String logicalPlantNo;
    private String mapCode;
    private String mapSubCode;
    private String currency;
    private BigDecimal exchangeRate;
    private Boolean calMap;
    private String operator;
    private String tenantId;
    private String version;
}
