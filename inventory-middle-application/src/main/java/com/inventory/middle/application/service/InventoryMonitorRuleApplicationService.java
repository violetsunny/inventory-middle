package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;

import java.util.List;


/**
 * 库存预警规则ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleApplicationService {

    /**
     * 保存
     *
     * @param inventorymonitorruleCommand
     */
    boolean add(InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * 更新
     *
     * @param inventorymonitorruleCommand
     */
    boolean update(InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

    /**
     * 处理预警规则 MQ 消息（根据 beanName 路由到对应 Handle Chain）
     *
     * @param monitorMessageBO 预警规则消息体
     */
    void processMonitorMessage(MonitorMessageBO monitorMessageBO);

    /**
     * 处理出入库库存变化消息（触发 monitorRuleInventorySnapshotHandleChain）
     *
     * @param changeMessage 库存变化消息
     */
    void processInOutBoundMessage(com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage changeMessage);

    /**
     * 执行年检预警计算（定时任务触发）：
     * 分页查询启用中的年检预警规则，逐条触发 monitorAnnualInspectionHandleChain
     */
    void processAnnualInspection();


    /**
     * 更新预警规则及其明细（事务，先删旧明细再批量新增）
     */
    boolean updateWithLines(com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand ruleCommand,
                            java.util.List<com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand> lineCommands);

}
