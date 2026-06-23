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
public class InventoryMonitorRuleRequest implements Serializable {

    private static final long serialVersionUID = -7450654186226007561L;

    private InventoryMonitorRuleBO monitorRuleBO;

    private List<InventoryMonitorRuleLineBO> ruleLineBOList;

}
