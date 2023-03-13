package com.inventory.middle.domain.common.constants;

/**
 * @author dongguo
 */
public class CommonConstant {

    public static final String VALIDATE_MESSAGE = "请求参数错误,提示信息：%s";

    public static final String REQUEST_IS_NULL = "请求对象为null";

    public static final String REQUEST_PARAM_IS_NULL = "请求参数为null";

    /**
     * email正则
     */
    public static final String EMAIL_REGULAR = "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";

    public static final String ALERT_CONTENT = "逻辑仓：【%s】下的物料：【%s】%s，偏差：%s";

    public static final String IN_TRANSIT_STOCK_BATCH_NO = "InTransitStock";

    public static final int PARTITION_SIZE = 500;

    public static final int EMAIL_PARTITION_SIZE = 100;

    public static final int NUM_ZERO = 0;

    public static final int NUM_ONE = 1;

    public static final int NUM_1000 = 1000;

    public static final int NUM_10000 = 10000;

    public static final int NUM_30000 = 30000;

    public static final String SEMICOLON = "；";

    public static final String SEMICOLON_RETURN = "；\n\t";

    public static final String INC = "+";

    public static final String DEC = "-";

    public static final String EMPTY = "";

    public static final String COMMA = ",";

    public static final String BLANK_SPACE = " ";

    public static final String ZERO = "0";

    public static final String TIME_9999 = "9999-12-30 00:00:00";

    public static final String ANNUAL_DATE = "annualDate";

    public static final String ANNUAL_CYCLE_DAYS = "annualCycleDays";

    public static final String ANNUAL_INFO = "annualInfo";

    public static final String SKU_SUPPLEMENT = "sku_supplement";

    public static final String TEXT_BOX = "textbox";
    public static final String POSITIVE_INTEGER_REG = "^\\d+$";
}
