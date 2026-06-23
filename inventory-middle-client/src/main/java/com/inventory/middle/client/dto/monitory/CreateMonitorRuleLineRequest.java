package com.inventory.middle.client.dto.monitory;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class CreateMonitorRuleLineRequest extends BaseRequest {

    private static final long serialVersionUID = -788305363015953268L;
    /**
     * 新增的规则行
     */
    private List<InventoryMonitorRuleLineInfoDTO> createList;

    /**
     * 预警规则id
     */
    @NotNull(message = "预警规则ID不能为空")
    private Long monitorRuleId;

    /**
     * 预警类型
     */
    @NotBlank(message = "预警规则类型不能为空")
    private String monitorType;




}
