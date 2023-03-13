package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存报警通知记录领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryAlertNotification implements Entity<InventoryAlertNotification> {

		private InventoryAlertNotificationId id;
		    		
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
	    
    @Override
    public boolean sameIdentityAs(InventoryAlertNotification other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
