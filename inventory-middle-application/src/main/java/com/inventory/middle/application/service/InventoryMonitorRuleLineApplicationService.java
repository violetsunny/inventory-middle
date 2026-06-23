package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 库存预警规则明细ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleLineApplicationService {

    /**
     * 保存
     *
     * @param inventorymonitorrulelineCommand
     */
    boolean add(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * 更新
     *
     * @param inventorymonitorrulelineCommand
     */
    boolean update(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

    /**
     * Excel 批量导入预警规则明细
     * @param monitorRuleId 规则ID
     * @param monitorType 预警类型
     * @param file 上传的 Excel 文件
     * @param tenantId 租户ID
     * @return 导入任务 key（用于后续查询结果）
     */
    String importByExcel(Long monitorRuleId, String monitorType, MultipartFile file, String tenantId);

}
