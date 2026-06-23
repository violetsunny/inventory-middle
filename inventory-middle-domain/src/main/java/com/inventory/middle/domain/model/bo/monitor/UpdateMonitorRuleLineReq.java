package com.inventory.middle.domain.model.bo.monitor;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class UpdateMonitorRuleLineReq implements Serializable {

    private static final long serialVersionUID = -1052121694864738467L;

    /**
     * 新增的规则行
     */
    private List<InventoryMonitorRuleLineBO> createList;

    /**
     * 更新的规则行
     */
    private List<InventoryMonitorRuleLineBO> updateList;

    /**
     * 删除的规则行
     */
    private List<Long> deleteList;

    /**
     * 预警规则ID
     */
    private Long monitorRuleId;

    /**
     * 操作人ID
     */
    private String operatorId;

}
