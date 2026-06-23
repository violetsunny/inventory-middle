package com.inventory.middle.client.dto.monitory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.inventory.middle.client.dto.BaseRequest;

import lombok.Data;

import java.util.List;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class UpdateInventoryMonitorRequest extends UpdateInventoryMonitorRuleRequest {


    private static final long serialVersionUID = 3443679710242313271L;

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


}
