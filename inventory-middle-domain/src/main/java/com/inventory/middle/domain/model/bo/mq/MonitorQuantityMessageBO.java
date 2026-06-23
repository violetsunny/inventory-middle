package com.inventory.middle.domain.model.bo.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预警规则数量提醒消息体
 * @author dongguo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorQuantityMessageBO implements Serializable {

    private static final long serialVersionUID = -3789880664052921517L;
    /**
     * 租户id
     */
    private String tenantId;


    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 库存量
     */
    private BigDecimal quantity;


}
