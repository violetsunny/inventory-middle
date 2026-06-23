package com.inventory.middle.client.dto.monitory;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class CreateInventoryMonitorRuleRequest extends BaseRequest {

    private static final long serialVersionUID = -7450654186226007561L;

    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    @Size(max = 32, message = "租户ID过长，最大32")
    private String tenantId;

    /**
     * 预警发送方式
     */
    @NotBlank(message = "预警发送方式不能为空")
    @Size(max = 32, message = "预警发送方式过长，最大32")
    private String monitorSendMode;

    /**
     * 发送地址
     */
    @Size(max = 256, message = "发送地址过长，最大256")
    private String monitorSendAddress;

    /**
     * 预警类型
     */
    @NotBlank(message = "预警类型不能为空")
    @Size(max = 32, message = "预警类型过长，最大32")
    private String monitorType;

    /**
     * 规则启用状态
     */
    @NotBlank(message = "规则启用状态不能为空")
    @Size(max = 32, message = "规则启用状态过长，最大32")
    private String monitorEnableStatus;

    /**
     * 预警维度
     */
    @NotBlank(message = "预警维度不能为空")
    @Size(max = 32, message = "预警维度过长，最大32")
    private String monitorDimension;

    /**
     * 操作人id
     */
    @NotNull(message = "操作人ID不能为空")
    @Size(max = 32, message = "操作人ID过长，最大32")
    private String operatorId;

}
