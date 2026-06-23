package com.inventory.middle.domain.model.bo.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 告警信息消息体
 * @author dongguo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryAlertMessageBO implements Serializable {

    private static final long serialVersionUID = -3789880664052921517L;

    /**
     * 预警规则ID
     */
    private Long monitorRuleId;

}
