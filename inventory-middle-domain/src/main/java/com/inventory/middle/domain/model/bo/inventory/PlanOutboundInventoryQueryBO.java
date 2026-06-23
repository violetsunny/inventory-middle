package com.inventory.middle.domain.model.bo.inventory;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查询计划出库查询请求BO
 *
 * @author vincent.li
 * @date 2021/9/29
 */
@Data
public class PlanOutboundInventoryQueryBO extends PageRequest implements Serializable {

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 物料code
     */
    private String materialCode;
    /**
     * 逻辑库存Id
     */
    private Long logicalPlantId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}
