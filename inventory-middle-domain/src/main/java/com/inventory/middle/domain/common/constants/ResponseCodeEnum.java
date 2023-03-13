package com.inventory.middle.domain.common.constants;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    SUCCESS("0", "成功"),
    FAILED("1", "失败"),
    DATA_NOT_EXISTS("3", "数据不存在"),
    SYSTEM_ERROR("99", "系统异常"),
    REPEAT_REQUEST("97", "重复请求"),
    WAIT_IN_LINE("996", "等待其他用户操作完成"),
    PARAM_IS_NULL("997", "请求参数为空"),
    REMOTE_SERVICE_FAILED("998", "远程服务调用异常"),
    PARAM_VALID_ERROR("999", "请求参数错误"),

    WAREHOUSE_NANME_EXIST("10001", "物理仓库名称已经存在"),
    WAREHOUSE_NOT_EXIST("10002", "物理仓库不存在"),
    LOGICAL_PLANT_NOT_EXIST("10003", "逻辑仓库不存在"),
    STORAGE_LOCATION_NOT_EXIST("10004", "存储地点不存在"),
    LOGICAL_PLANT_NANME_EXIST("10005", "逻辑仓库名称已经存在"),
    STORAGE_LOCATION_NANME_EXIST("10006", "存储地点名称已经存在"),
    SPECIAL_STORAGE_LOCATION_EXIST("10007", "该特殊类型存储地点已经存在"),

    /**
     * errorCode : 20001 ~ 29999 预警规则使用
     */
    LOGICAL_PLANT_MATERIAL_EXISTS("20001", "预警规则中已经存在有效的该逻辑仓的预警维度"),
    ASSIGN_MATERIAL_EXISTS("20002", "预警规则中已经存在有效的该物料的预警维度"),
    MONITOR_RULE_CREATE_FAILED("20003", "预警规则创建失败"),
    MONITOR_RULE_UPDATE_FAILED("20004", "预警规则更新失败"),
    MONITOR_RULE_DELETE_FAILED("20005", "预警规则删除失败"),
    MONITOR_MATERIAL_REPEATED("20006", "预警数据重复"),
    MONITOR_MATERIAL_EXISTS("20007", " 预警规则中已存在该物料"),
    MONITOR_NOT_EXISTS("20008", "预警规则不存在"),
    MONITOR_RULE_LINE_CREATE_FAILED("20009", "预警规则行创建失败"),
    MONITOR_RULE_LINE_UPDATE_FAILED("20010", "预警规则行保存失败"),
    MONITOR_RULE_LINE_IS_NULL("20011", "预警规则行ID为空"),
    MONITOR_SEND_MODE_ERROR("20012", "预警发送方式错误"),
    MONITOR_EMAIL_IS_NULL("20013", "预警发送邮箱为空"),
    MONITOR_EMAIL_ERROR("20014", "预警发送邮箱格式错误"),
    MONITOR_TYPE_ERROR("20015", "预警类型错误"),
    MONITOR_ENABLE_STATUS_ERROR("20016", "预警规则启停用状态错误"),
    MONITOR_DIMENSION_ERROR("20017", "预警维度错误"),
    MONITOR_LOGICAL_PLANT_IS_NULL("20018", "预警规则逻辑仓为空"),
    MONITOR_CEIL_FLOOR_IS_NULL("20019", "上限和下限不能同时为空"),
    MONITOR_CEIL_ERROR("20020", "上限必须大于0"),
    MONITOR_FLOOR_ERROR("20021", "下限必须大于等于0"),
    MONITOR_CEIL_FLOOR_ERROR("20022", "上限必须要大于下限"),
    MONITOR_LOGICAL_PLANT_EXISTS("20023", "该逻辑仓已有有效的预警规则"),
    MONITOR_MATERIAL_LOGICAL_PLANT_EXISTS("20024", "新增的物料或者逻辑仓已有有效的预警规则"),
    MONITOR_ENABLE_FAILED_RULE_LINE("20025", "预警规则无有效的预警行信息，不能启用规则"),
    MONITOR_DIMENSION_UNIQUE_ERROR("20026", "预警规则行只能使用一种预警维度"),
    MONITOR_RULE_UPDATE_FAILED_WITH_REASON("20027", "预警规则更新失败，%s已有有效的预警规则"),
    MONITOR_RULE_ALERT_CREATE_FAILED("20028", "预警规则日志创建失败"),
    MONITOR_RULE_ALERT_DELETE_FAILED("20029", "预警规则日志删除失败"),
    MONITOR_RULE_ADVANCE_QTY_IS_NULL("20030", "预警规则提前量不能为空"),
    MONITOR_RULE_ADVANCE_ERROR("20031", "预警规则提前量必须为正整数"),
    MONITOR_RULE_MATERIAL_ERROR("20032", "物料%s已在规则中存在"),


    IN_OUT_INCONSISTENT("30001", "库存转移，出库入库批次须一致"),
    MATERIAL_BATCH("30002", "物料批次数据不存在"),
    NO_INVENTORY("30002", "查不到库存数据"),
    MDOC_NOT_EXIST("30003", "物料凭证号不存在"),
    MDOC_OUTBOUND_REPEAT_SAMETIME("30004", "相同物料在同一逻辑仓内同一时间只能有一个出库操作"),
    MDOC_INBOUND_CANCEL_REPEAT_SAMETIME("30005", "相同物料在同一逻辑仓内同一时间只能有一个入库冲销操作"),
    TRANSFER_TO_STOCK_REPEAT_SAMETIME("30006", "相同物料在同一逻辑仓内同一时间只能有一个在途转在库操作"),
    TRANSFER_QUANTITY_TO_LARGE("30007", "转换数量不能大于已存在的在途库存量"),
    TRANSFER_TRANSIT_STOCK_TO_IN("30008","入库无法调整在途库存"),
    OUT_ERROR("30009","出库异常"),
    ;

    ResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public static ResponseCodeEnum getByCode(String code) {
        for (ResponseCodeEnum e : ResponseCodeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

}
