/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 库存预警操作类型
 * @author dongguo.tao
 * @version
 */
@AllArgsConstructor
@Getter
public enum InventoryAlertActionEnum {

    MATERIAL_INVENTORY_SNAPSHOT("库存物料快照变动","monitorRuleInventorySnapshotHandleChain"),
    MONITOR_RULE_UPDATE( "预警规则更新","monitorRuleUpdateHandleChain"),
    MONITOR_RULE_LINE( "预警规则行创建/更新","monitorRuleLineHandleChain"),
    MONITOR_RULE_DELETE( "预警规则删除","monitorRuleDeleteHandleChain"),
    ANNUAL_INSPECTION( "年检预警","monitorAnnualInspectionHandleChain"),
    ;

    private String desc;
    /**
     * 处理的bean
     */
    private String beanName;

}
