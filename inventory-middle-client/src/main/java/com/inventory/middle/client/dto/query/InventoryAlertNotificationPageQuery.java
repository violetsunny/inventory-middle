package com.inventory.middle.client.dto.query;


import lombok.Data;
import top.kdla.framework.dto.PageQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 库存报警通知记录PageQuery
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */

@Data
public class InventoryAlertNotificationPageQuery extends PageQuery {
    
    /**
     * 库存报警通知记录id
     */
    private Long id;
    
    /**
     * 报警日志id
     */
    private Long alertId;
    
    /**
     * 通知方式
     */
    private String notificationMode;
    
    /**
     * 通知地址（手机/邮箱）
     */
    private String notificationAddress;
    
    /**
     * 通知内容
     */
    private String content;
    
    /**
     * 通知url地址
     */
    private String url;
    
    /**
     * 通知状态（是否发送）
     */
    private Integer status;
    
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
    
}

