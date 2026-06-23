package com.inventory.middle.client.dto.monitory;


import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dongguo
 */
@Data
public class InventoryMonitorRuleLineInfoDTO implements Serializable {


    private static final long serialVersionUID = 872472569468549505L;


    @NotNull(message = "预警规则ID不能为空")
    private Long monitorRuleId;

    /**
     * 预警维度
     */
    @NotBlank(message = "预警维度不能为空")
    @Size(max = 32, message = "省预警维度过长，最大32")
    private String monitorDimension;

    /**
     * 预警对象
     */
    @NotBlank(message = "预警对象不能为空")
    @Size(max = 32, message = "省预警对象过长，最大32")
    private String monitorObject;

    /**
     * 上限
     *
     */
    @Digits(integer= 14, fraction= 6, message = "库存上限最大支持14位整数6位小数")
    private BigDecimal monitorCeil;

    /**
     * 下限
     */
    @Digits(integer= 14, fraction= 6, message = "库存下限最大支持14位整数6位小数")
    private BigDecimal monitorFloor;

    /**
     * 操作人id
     */
    @NotBlank(message = "操作人ID不能为空")
    @Size(max = 32, message = "操作人ID过长，最大32")
    private String operatorId;


}
