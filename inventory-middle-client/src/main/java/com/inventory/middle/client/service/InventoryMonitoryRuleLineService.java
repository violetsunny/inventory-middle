package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.monitory.*;

/**
 * @author dongguo.tao
 * @desc 库存预警规则行接口
 */
public interface InventoryMonitoryRuleLineService {

    /**
     * 根据预警规则ID查询预警行信息
     * @param pageRequest
     * @return
     */
    top.kdla.framework.dto.PageResponse<InventoryMonitorRuleLineResponse> pageRuleLineList(InventoryMonitorRuleLinePageRequest pageRequest);

    /**
     * 批量新增预警规则行
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<BatchCreateMonitorRuleLineResponse> batchCreateRuleLine(CreateMonitorRuleLineRequest request);

    /**
     * 更新预警规则行
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> updateRuleLine(UpdateMonitorRuleLineRequest request);

}
