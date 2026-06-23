package com.inventory.middle.client.dto.monitory;

import com.inventory.middle.client.enums.monitor.MonitorRuleTypeEnum;
import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class UpdateMonitorRuleLineRequest extends BaseRequest {

    private static final long serialVersionUID = 815974966777973231L;

    /**
     * 更新的规则行
     */
    private List<InventoryMonitorRuleLineDTO> updateList;

    /**
     * 新增的规则行
     */
    private List<InventoryMonitorRuleLineInfoDTO> createList;

    /**
     * 删除的规则行
     */
    private List<Long> deleteList;

    /**
     * 预警规则id
     */
    @NotNull(message = "预警规则ID不能为空")
    private Long monitorRuleId;

    /**
     * 预警类型
     * @see MonitorRuleTypeEnum
     */
    @NotNull(message = "预警类型不能为空")
    private String monitorType;

    /**
     * 操作人ID
     */
    @NotBlank(message = "操作人不能为空")
    private String operatorId;



}
