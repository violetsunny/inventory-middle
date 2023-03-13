package com.inventory.middle.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 库存报警通知记录Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("inventory_alert_notification")
public class InventoryAlertNotificationDo implements Serializable {
    
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

