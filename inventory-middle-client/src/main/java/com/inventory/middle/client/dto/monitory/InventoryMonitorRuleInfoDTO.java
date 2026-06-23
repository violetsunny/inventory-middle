package com.inventory.middle.client.dto.monitory;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryMonitorRuleInfoDTO implements Serializable {

    private static final long serialVersionUID = 341770730242624927L;

    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    @Size(max = 32, message = "租户ID过长，最大32")
    private String tenantId;

    /**
     * 预警规则CODE
     */
    @Size(max = 32, message = "预警规则CODE过长，最大32")
    private String monitorRuleCode;

    /**
     * 预警发送方式
     */
    @NotBlank(message = "预警发送方式不能为空")
    @Size(max = 32, message = "预警发送方式过长，最大32")
    private String monitorSendMode;

    /**
     * 发送地址
     */
    @Size(max = 256, message = "预警发送方式过长，最大256")
    private String monitorSendAddress;

    /**
     * 预警类型
     */
    @NotBlank(message = "预警类型不能为空")
    @Size(max = 32, message = "预警类型过长，最大32")
    private String monitorType;

    /**
     * 预警维度
     */
    @NotBlank(message = "预警维度不能为空")
    @Size(max = 32, message = "预警维度过长，最大32")
    private String monitorDimension;

    /**
     * 任务间隔（分钟）
     */
    private Integer monitorInterval;

    /**
     * 规则启用状态
     */
    @NotBlank(message = "规则启用状态不能为空")
    @Size(max = 32, message = "规则启用状态过长，最大32")
    private String monitorEnableStatus;


    /**
     * 操作人id
     */
    @NotNull(message = "操作人ID不能为空")
    @Size(max = 32, message = "操作人ID过长，最大32")
    private String operatorId;

}
