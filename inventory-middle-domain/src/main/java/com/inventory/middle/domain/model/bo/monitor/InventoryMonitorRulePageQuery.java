package com.inventory.middle.domain.model.bo.monitor;

import com.inventory.middle.client.dto.PageRequest;
import com.inventory.middle.client.enums.monitor.MonitorRuleTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class InventoryMonitorRulePageQuery extends PageRequest implements Serializable {

    private static final long serialVersionUID = -5347237944867761866L;
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 预警规则编码
     */
    private String monitorRuleCode;

    /**
     * 规则启用状态
     */
    private String monitorEnableStatus;

    /**
     * 预警规则类型
     *
     * @see MonitorRuleTypeEnum
     */
    private String monitorType;

}
