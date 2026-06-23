package com.inventory.middle.domain.model.bo.monitor;

import java.io.Serializable;
import java.util.List;

import com.inventory.middle.domain.model.bo.BaseBO;

import lombok.Data;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class UpdateMonitorRuleReq extends InventoryMonitorRuleBO implements Serializable {


    private static final long serialVersionUID = -8081673986942237466L;

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



}
