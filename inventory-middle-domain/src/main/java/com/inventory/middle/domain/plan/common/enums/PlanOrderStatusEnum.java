package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

/**
 * 计划订单状态枚举
 *
 * @author peisheng.wang
 */
@Getter
public enum PlanOrderStatusEnum {

    CREATED(0, "已创建"),
    CONFIRMED(1, "已确认"),
    INCOMPLETE_ISSUE(2, "已部分下发"),
    COMPLETE_ISSUE(3, "已完全下发"),
    FINISHED(4, "已完结"),
    OVERDUE(5, "已逾期"),
    ISSUE_OVERDUE(6, "下发逾期"),
    FINISH_OVERDUE(7, "完结逾期"),
    ;

    PlanOrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public static PlanOrderStatusEnum getByCode(Integer code) {
        for (PlanOrderStatusEnum e : PlanOrderStatusEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
