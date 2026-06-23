package com.inventory.middle.domain.plan.common.constants;

/**
 * 计划公共常量 - 别名类，委托给 PlanCommonConstants
 * <p>
 * 迁移自 com.enn.plan.management.core.common.constants.CommonConstants
 * </p>
 */
public class CommonConstants {

    private CommonConstants() {
    }

    public static final String TRANSFERRED_PLUS = PlanCommonConstants.TRANSFERRED_PLUS;
    public static final String TRANSFERRED_BLANK = PlanCommonConstants.TRANSFERRED_BLANK;

    public static final String DOT_XLS = PlanCommonConstants.DOT_XLS;
    public static final String DOT_XLSX = PlanCommonConstants.DOT_XLSX;

    public static final int KB_SIZE = PlanCommonConstants.KB_SIZE;

    public static final String POSITIVE_INTEGER_PATTERN = PlanCommonConstants.POSITIVE_INTEGER_PATTERN;
    public static final String INTEGER_PATTERN = PlanCommonConstants.INTEGER_PATTERN;
    public static final String PERCENTAGE_PATTERN = PlanCommonConstants.PERCENTAGE_PATTERN;

    public static final String PLACEHOLDER_$ = PlanCommonConstants.PLACEHOLDER_$;
    public static final String PLAN_CODE = PlanCommonConstants.PLAN_CODE;
    public static final String PLAN_ORDER_NO = PlanCommonConstants.PLAN_ORDER_NO;
    public static final String ISSUE_ORDER_NO = PlanCommonConstants.ISSUE_ORDER_NO;
    public static final String BOM_NO = PlanCommonConstants.BOM_NO;

    public static final Integer INTEGER_ZERO = PlanCommonConstants.INTEGER_ZERO;
    public static final Integer INTEGER_ONE = PlanCommonConstants.INTEGER_ONE;
    public static final Integer INTEGER_180 = PlanCommonConstants.INTEGER_180;

    public static final Integer MAX_PERCENT_NUMBER = PlanCommonConstants.MAX_PERCENT_NUMBER;

    public static final String DELIMITER_COMMA = PlanCommonConstants.DELIMITER_COMMA;
    public static final String DELIMITER_HYPHEN = PlanCommonConstants.DELIMITER_HYPHEN;

    public static final Integer MAX_LENGTH_40 = PlanCommonConstants.MAX_LENGTH_40;
    public static final String PLAN_VERSION_SEQUENCE_PREFIX = PlanCommonConstants.PLAN_VERSION_SEQUENCE_PREFIX;
    public static final String PROJECT_ODER_PREFIX = PlanCommonConstants.PROJECT_ODER_PREFIX;
}
