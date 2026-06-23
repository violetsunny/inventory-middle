package com.inventory.middle.domain.plan.common.constants;

/**
 * 计划公共常量
 *
 * @author peisheng.wang
 */
public class PlanCommonConstants {

    private PlanCommonConstants() {
    }

    public static final String TRANSFERRED_PLUS = "\\+";
    public static final String TRANSFERRED_BLANK = "%20";

    public static final String DOT_XLS = ".xls";
    public static final String DOT_XLSX = ".xlsx";

    public static final int KB_SIZE = 1024;

    /** 正则表达式：正整数 */
    public static final String POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";

    public static final String INTEGER_PATTERN = "^\\+?\\d+$";

    /** 正则表达式：百分比（两位小数） */
    public static final String PERCENTAGE_PATTERN = "^(\\d{1,2}(\\.[0-9]{1,2})?)$|^100(\\.[0]{1,2})$|^100";

    /**
     * 占位符
     */
    public static final String PLACEHOLDER_$ = "$";

    /**
     * 计划方案号前缀
     */
    public static final String PLAN_CODE = "PV$";

    /**
     * 计划订单号前缀
     */
    public static final String PLAN_ORDER_NO = "PO$";

    /**
     * 下发订单号前缀
     */
    public static final String ISSUE_ORDER_NO = "IO$";

    /**
     * BOM单号前缀
     */
    public static final String BOM_NO = "BOM";

    /**
     * int类型 0
     */
    public static final Integer INTEGER_ZERO = 0;

    /**
     * int类型 1
     */
    public static final Integer INTEGER_ONE = 1;

    /**
     * int类型 180
     */
    public static final Integer INTEGER_180 = 180;

    /**
     * 百分比最大值 因为是按照 百分比 * 100来存储 所以最大值是100 * 100 = 10000
     */
    public static final Integer MAX_PERCENT_NUMBER = 10000;

    /**
     * 逗号分隔符
     */
    public static final String DELIMITER_COMMA = ",";

    /**
     * - 分隔符
     */
    public static final String DELIMITER_HYPHEN = "-";

    /**
     * 字符串长度限制
     */
    public static final Integer MAX_LENGTH_40 = 40;

    /**
     * 物料计划参数序列规则前缀定义
     */
    public static final String PLAN_VERSION_SEQUENCE_PREFIX = "PI_VERSION";

    /**
     * 项目订单需求序列规则前缀
     */
    public static final String PROJECT_ODER_PREFIX = "M";
}
