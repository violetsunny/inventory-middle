package com.inventory.middle.client.dto.monitory;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class UpdateInventoryMonitorRuleRequest extends BaseRequest {

    private static final long serialVersionUID = -7450654186226007561L;

    /**
     * 规则id
     */
    @NotNull(message = "预警规则ID不能为空")
    private Long id;

    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    /**
     * 预警发送方式
     */
    @NotBlank(message = "预警发送方式不能为空")
    private String monitorSendMode;

    /**
     * 发送地址
     */
    private String monitorSendAddress;

    /**
     * 预警类型
     */
    @NotBlank(message = "预警类型不能为空")
    private String monitorType;

    /**
     * 预警维度
     */
    @NotBlank(message = "预警维度不能为空")
    private String monitorDimension;

    /**
     * 任务间隔（分钟）
     */
    private Integer monitorInterval;

    /**
     * 规则启用状态
     */
    @NotBlank(message = "规则启用状态不能为空")
    private String monitorEnableStatus;

    /**
     * 操作人id
     */
    @NotNull(message = "操作人ID不能为空")
    private String operatorId;

}
