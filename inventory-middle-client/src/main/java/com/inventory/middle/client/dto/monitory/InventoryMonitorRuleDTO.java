package com.inventory.middle.client.dto.monitory;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryMonitorRuleDTO extends InventoryMonitorRuleInfoDTO implements Serializable {

    private static final long serialVersionUID = 7301824466815487100L;

    /**
     * 规则id
     */
    @NotNull(message = "预警规则ID不能为空")
    private Long id;


}
