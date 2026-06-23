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
public class DeleteInventoryMonitorRuleRequest extends BaseRequest {

    private static final long serialVersionUID = -4665133413628968889L;

    /**
     * 预警规则ID
     */
    @NotNull(message = "预警规则ID不能为空")
    private Long monitorRuleId;

    /**
     * 租户ID
     */
    @NotBlank(message = "租户ID不能为空")
    @Size(max = 32, message = "租户ID过长，最大32")
    private String tenantId;

    /**
     * 操作人ID
     */
    @NotNull(message = "操作人ID不能为空")
    @Size(max = 32, message = "操作人ID过长，最大32")
    private String operatorId;

}
