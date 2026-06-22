package com.inventory.middle.client.dto.command;


import top.kdla.framework.validator.group.UpdateGroup;
import top.kdla.framework.dto.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 库存报警通知记录Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存报警通知记录", description = "库存报警通知记录")
public class InventoryAlertNotificationCommand extends Command {

	/**
	 * 库存报警通知记录id
	 */
	@Schema(description = "库存报警通知记录id")
			@NotNull(message = "库存报警通知记录id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 报警日志id
	 */
	@Schema(description = "库存报警通知记录alertId")
			@NotNull(message = "库存报警通知记录alertId 不能为空", groups = UpdateGroup.class)
		private Long alertId;

	/**
	 * 通知方式
	 */
	@Schema(description = "库存报警通知记录notificationMode")
		@NotBlank(message = "库存报警通知记录notificationMode 不能为空", groups = UpdateGroup.class)
			private String notificationMode;

	/**
	 * 通知地址（手机/邮箱）
	 */
	@Schema(description = "库存报警通知记录notificationAddress")
		@NotBlank(message = "库存报警通知记录notificationAddress 不能为空", groups = UpdateGroup.class)
			private String notificationAddress;

	/**
	 * 通知内容
	 */
	@Schema(description = "库存报警通知记录content")
		@NotBlank(message = "库存报警通知记录content 不能为空", groups = UpdateGroup.class)
			private String content;

	/**
	 * 通知url地址
	 */
	@Schema(description = "库存报警通知记录url")
		@NotBlank(message = "库存报警通知记录url 不能为空", groups = UpdateGroup.class)
			private String url;

	/**
	 * 通知状态（是否发送）
	 */
	@Schema(description = "库存报警通知记录status")
			@NotNull(message = "库存报警通知记录status 不能为空", groups = UpdateGroup.class)
		private Integer status;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存报警通知记录createTime")
			@NotNull(message = "库存报警通知记录createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存报警通知记录creatorId")
		@NotBlank(message = "库存报警通知记录creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 更新时间
	 */
	@Schema(description = "库存报警通知记录updateTime")
			@NotNull(message = "库存报警通知记录updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 更新人id
	 */
	@Schema(description = "库存报警通知记录updatorId")
		@NotBlank(message = "库存报警通知记录updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识 0-未删除；1-已删除
	 */
	@Schema(description = "库存报警通知记录deleted")
			@NotNull(message = "库存报警通知记录deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

}
