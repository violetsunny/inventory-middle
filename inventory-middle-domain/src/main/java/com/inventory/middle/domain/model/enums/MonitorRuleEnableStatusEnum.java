package com.inventory.middle.domain.model.enums;

import lombok.Getter;

@Getter
public enum MonitorRuleEnableStatusEnum {
    ENABLE("启用"),
    DISABLE("停用");

    private final String desc;
    MonitorRuleEnableStatusEnum(String desc) { this.desc = desc; }
}
