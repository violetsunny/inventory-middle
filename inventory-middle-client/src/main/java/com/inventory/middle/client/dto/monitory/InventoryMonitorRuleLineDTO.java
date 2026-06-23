package com.inventory.middle.client.dto.monitory;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dongguo
 */
@Data
public class InventoryMonitorRuleLineDTO extends InventoryMonitorRuleLineInfoDTO implements Serializable {

    private static final long serialVersionUID = -9174798814816387683L;
    /**
     * 规则明细id
     */
    @NotNull(message = "预警规则行ID不能为空")
    private Long id;

}
