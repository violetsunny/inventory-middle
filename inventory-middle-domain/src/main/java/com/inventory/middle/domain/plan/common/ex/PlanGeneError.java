package com.inventory.middle.domain.plan.common.ex;

/**
 * 计划产出错误码枚举
 * <p>
 * 迁移自 com.enn.plan.management.core.common.ex.PlanGeneError
 * </p>
 */
public enum PlanGeneError implements ErrorCode {

    CALCULATE_ERROR("PI_001", "计划计算异常", "计划计算异常，请联系管理员"),
    PARENT_NODE_NOT_EXIST("PI_002", "父节点计划不存在", "父节点计划不存在，无法生成子节点计划"),
    PARAMETER_NOT_EXIST("PI_003", "物料计划参数不存在", "物料计划参数不存在，请先配置参数");

    private final String code;
    private final String message;
    private final String userTip;

    PlanGeneError(String code, String message, String userTip) {
        this.code = code;
        this.message = message;
        this.userTip = userTip;
    }

    @Override
    public String errCode() {
        return code;
    }

    @Override
    public String errMessage() {
        return message;
    }

    @Override
    public String userTip() {
        return userTip;
    }
}
