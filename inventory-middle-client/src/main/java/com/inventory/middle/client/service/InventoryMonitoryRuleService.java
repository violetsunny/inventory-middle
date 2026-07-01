package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.KeyValueDTO;
import com.inventory.middle.client.dto.monitory.*;

import java.util.ArrayList;

/**
 * @author dongguo.tao
 * @desc 库存预警规则接口
 */
public interface InventoryMonitoryRuleService {

    /**
     * 库存预警规则状态查询
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<KeyValueDTO>> queryMonitorRuleType();

    /**
     * 库存预警规则发送方式查询
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<KeyValueDTO>> queryMonitorSendMode();

    /**
     * 库存预警维度查询
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<KeyValueDTO>> queryMonitorDimension();

    /**
     * 库存预警规则启用状态查询
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<KeyValueDTO>> queryMonitorEnableStatus();

    /**
     * 库存预警规则创建
     * @param createRequest
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Long> createMonitorRule(CreateInventoryMonitorRuleRequest createRequest);

    /**
     * 库存预警规则更新
     * @param updateRequest
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> updateMonitorRule(UpdateInventoryMonitorRuleRequest updateRequest);

    /**
     * 库存预警规则和行项目更新
     * @param updateRequest
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> updateMonitorRuleAndLine(UpdateInventoryMonitorRequest updateRequest);

    /**
     * 库存预警规则删除
     * @param deleteRequest
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> deleteMonitorRule(DeleteInventoryMonitorRuleRequest deleteRequest);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    top.kdla.framework.dto.PageResponse<InventoryMonitorRuleResponse> pageList(InventoryMonitorRulePageRequest pageRequest);

    /**
     * 根据ID查询规则
     * @param monitorRuleId
     * @return
     */
    top.kdla.framework.dto.SingleResponse<InventoryMonitorRuleResponse> queryById(Long monitorRuleId);

}
