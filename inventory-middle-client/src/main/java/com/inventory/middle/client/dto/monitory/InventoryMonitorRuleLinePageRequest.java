package com.inventory.middle.client.dto.monitory;

import com.inventory.middle.client.dto.PageRequest;
import com.inventory.middle.client.enums.monitor.MonitorRuleTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 库存预警规则行
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class InventoryMonitorRuleLinePageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -8367658378320866661L;

    /**
     * 预警规则ID
     */
    @NotNull(message = "预警规则ID不能为空")
    private Long monitorRuleId;

    /**
     * 预警维度
     */
    @NotBlank(message = "预警维度不能为空")
    private String monitorDimension;

    /**
     * 预警类型
     * @see MonitorRuleTypeEnum
     */
    private String monitorType;




}
