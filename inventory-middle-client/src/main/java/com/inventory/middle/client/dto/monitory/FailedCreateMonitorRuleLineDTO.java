package com.inventory.middle.client.dto.monitory;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dongguo
 */
@Data
public class FailedCreateMonitorRuleLineDTO extends InventoryMonitorRuleLineInfoDTO implements Serializable {

    private static final long serialVersionUID = -1476602257734131884L;
    /**
     * 失败原因
     */
    private String failedReason;

}
