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
 * 库存预警规则Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存预警规则", description = "库存预警规则")
public class InventoryMonitorRuleCommand extends Command {

	/**
	 * 规则id
	 */
	@Schema(description = "库存预警规则id")
			@NotNull(message = "库存预警规则id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 规则code
	 */
	@Schema(description = "库存预警规则monitorRuleCode")
		@NotBlank(message = "库存预警规则monitorRuleCode 不能为空", groups = UpdateGroup.class)
			private String monitorRuleCode;

	/**
	 * 预警发送方式
	 */
	@Schema(description = "库存预警规则monitorSendMode")
		@NotBlank(message = "库存预警规则monitorSendMode 不能为空", groups = UpdateGroup.class)
			private String monitorSendMode;

	/**
	 * 发送地址
	 */
	@Schema(description = "库存预警规则monitorSendAddress")
		@NotBlank(message = "库存预警规则monitorSendAddress 不能为空", groups = UpdateGroup.class)
			private String monitorSendAddress;

	/**
	 * 预警类型
	 */
	@Schema(description = "库存预警规则monitorType")
		@NotBlank(message = "库存预警规则monitorType 不能为空", groups = UpdateGroup.class)
			private String monitorType;

	/**
	 * 预警维度
	 */
	@Schema(description = "库存预警规则monitorDimension")
		@NotBlank(message = "库存预警规则monitorDimension 不能为空", groups = UpdateGroup.class)
			private String monitorDimension;

	/**
	 * 任务间隔（分钟）
	 */
	@Schema(description = "库存预警规则monitorInterval")
			@NotNull(message = "库存预警规则monitorInterval 不能为空", groups = UpdateGroup.class)
		private Integer monitorInterval;

	/**
	 * 规则启用状态
	 */
	@Schema(description = "库存预警规则monitorEnableStatus")
		@NotBlank(message = "库存预警规则monitorEnableStatus 不能为空", groups = UpdateGroup.class)
			private String monitorEnableStatus;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存预警规则createTime")
			@NotNull(message = "库存预警规则createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存预警规则creatorId")
		@NotBlank(message = "库存预警规则creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 更新时间
	 */
	@Schema(description = "库存预警规则updateTime")
			@NotNull(message = "库存预警规则updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 更新人id
	 */
	@Schema(description = "库存预警规则updatorId")
		@NotBlank(message = "库存预警规则updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识 0-未删除；1-已删除
	 */
	@Schema(description = "库存预警规则deleted")
			@NotNull(message = "库存预警规则deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存预警规则tenantId")
		@NotBlank(message = "库存预警规则tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
