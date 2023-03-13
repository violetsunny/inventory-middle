/**
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 库存中心发送MQ消息的TAG
 * @author dongguo.tao
 * @version
 */
@AllArgsConstructor
@Getter
public enum InventoryTagEnum implements IEnum<String> {

    INBOUND("入库","inboundTag"),
    OUTBOUND( "出库","outboundTag"),
    REVERSE( "冲销","reverseTag"),
    MONITOR_RULE_CREATE( "预警规则创建","monitorRuleCreateTag"),
    MONITOR_RULE_UPDATE( "预警规则更新","monitorRuleUpdateTag"),
    MONITOR_RULE_DELETE( "预警规则删除","monitorRuleDeleteTag"),
    MONITOR_RULE_LINE_UPDATE( "预警规则更新","monitorRuleLineUpdateTag"),
    MONITOR_RULE_LINE_CREATE( "预警规则创建","monitorRuleLineCreateTag"),
    INVENTORY_ALERT_CREATE( "预警信息创建","inventoryAlertCreateTag"),
    MATERIAL_DOC_IN_CREATED( "入库物料凭证创建","materialDocInTag"),
    MATERIAL_DOC_CANCEL_CREATED( "取消物料凭证创建","materialDocCancelTag"),
    MONITOR_ANNUAL_INSPECTION( "年检预警提醒","monitorAnnualInspectionTag"),
    MONITOR_QUANTITY( "数量预警提醒","monitorQuantityTag"),
    ;

    private String desc;

    private String code;

}
