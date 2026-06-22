package com.inventory.middle.client.dto.query;


import lombok.Data;
import top.kdla.framework.dto.PageQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 库存预警规则PageQuery
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */

@Data
public class InventoryMonitorRulePageQuery extends PageQuery {
    
    /**
     * 规则id
     */
    private Long id;
    
    /**
     * 规则code
     */
    private String monitorRuleCode;
    
    /**
     * 预警发送方式
     */
    private String monitorSendMode;
    
    /**
     * 发送地址
     */
    private String monitorSendAddress;
    
    /**
     * 预警类型
     */
    private String monitorType;
    
    /**
     * 预警维度
     */
    private String monitorDimension;
    
    /**
     * 任务间隔（分钟）
     */
    private Integer monitorInterval;
    
    /**
     * 规则启用状态
     */
    private String monitorEnableStatus;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人id
     */
    private String creatorId;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 更新人id
     */
    private String updatorId;
    
    /**
     * 删除标识 0-未删除；1-已删除
     */
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    
}

