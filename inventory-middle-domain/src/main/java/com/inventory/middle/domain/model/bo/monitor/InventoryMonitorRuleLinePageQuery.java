package com.inventory.middle.domain.model.bo.monitor;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 库存预警规则行
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class InventoryMonitorRuleLinePageQuery extends PageRequest implements Serializable {

    private static final long serialVersionUID = -1785549642699468372L;
    /**
     * 预警规则ID
     */
    private Long monitorRuleId;

    /**
     * 预警维度
     */
    private String monitorDimension;

}
