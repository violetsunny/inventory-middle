package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

@Getter
public enum PlanResponseCodeEnum {

    SUCCESS("0", "成功"),
    FAILED("1", "失败"),

    PARAMETER_VALIDATE_FAIL("C_0001", "参数校验失败"),
    GET_LOGIN_USER_FAILED("C_0002", "获取登录用户信息失败"),
    REMOTE_SERVICE_FAILED("C_0003", "远程服务调用异常"),
    TENANT_ID_IS_NULL("C_0004", "租户ID不能为空"),
    USER_INFO_IS_NULL("C_0005", "用户信息不能为空"),
    DATA_IS_NULL("C_0006", "数据不存在"),
    DATA_IS_ERROR("C_0007", "数据异常"),
    NO_AUTH("C_8888", "越权操作"),
    SYSTEM_ERROR("C_9999", "系统异常"),

    D_CREATE_DEMAND_PLAN_FAIL("D_CREATE_DEMAND_PLAN_FAIL_0001", "创建需求计划失败"),
    D_CREATE_DEMAND_PLAN_INVALID_DAYS("D_CREATE_DEMAND_PLAN_FAIL_0002", "计划展望期不能大于180天"),
    D_CREATE_DEMAND_PLAN_INVALID_HORIZON_DATE("D_CREATE_DEMAND_PLAN_FAIL_0003", "计划展望期开始时间不能大于结束时间"),

    D_CREATE_DEMAND_PLAN_INVALID_FIRST_DAY("D_CREATE_DEMAND_PLAN_FAIL_0004", "计划展望期开始时间非周期第一天"),
    D_CREATE_DEMAND_PLAN_INVALID_LAST_DAY("D_CREATE_DEMAND_PLAN_FAIL_0005", "计划展望期开始时间非周期第一天"),
    D_CANCEL_DEMAND_PLAN_MATERIAL_FAIL("D_CREATE_DEMAND_PLAN_FAIL_0006", "剔除物料失败"),
    D_COMMON_STATUS_NOT_OFF("D_CREATE_DEMAND_PLAN_FAIL_0007", "计划非停用状态，不可修改数据"),
    D_MODIFY_DEMAND_PLAN_MATERIAL_AMOUNT_FAIL("D_CREATE_DEMAND_PLAN_FAIL_0008", "修改物料数量失败"),
    D_UPDATE_DEMAND_PLAN_FAIL("D_CREATE_DEMAND_PLAN_FAIL_0009", "修改需求计划失败"),
    D_DEMAND_PLAN_NOT_EXIST("D_CREATE_DEMAND_PLAN_FAIL_0010", "需求计划不存在"),

    ;

    PlanResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public static PlanResponseCodeEnum getByCode(String code) {
        for (PlanResponseCodeEnum e : PlanResponseCodeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
