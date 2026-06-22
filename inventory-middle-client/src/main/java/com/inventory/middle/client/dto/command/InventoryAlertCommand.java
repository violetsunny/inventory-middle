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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存报警日志Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存报警日志", description = "库存报警日志")
public class InventoryAlertCommand extends Command {

	/**
	 * 预警记录id
	 */
	@Schema(description = "库存报警日志id")
			@NotNull(message = "库存报警日志id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 预警规则id
	 */
	@Schema(description = "库存报警日志monitorRuleId")
			@NotNull(message = "库存报警日志monitorRuleId 不能为空", groups = UpdateGroup.class)
		private Long monitorRuleId;

	/**
	 * 物料编码
	 */
	@Schema(description = "库存报警日志materialCode")
		@NotBlank(message = "库存报警日志materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 逻辑仓库no
	 */
	@Schema(description = "库存报警日志logicalPlantNo")
		@NotBlank(message = "库存报警日志logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 偏差数量
	 */
	@Schema(description = "库存报警日志deviate")
			@NotNull(message = "库存报警日志deviate 不能为空", groups = UpdateGroup.class)
		private BigDecimal deviate;

	/**
	 * 报警标识
	 */
	@Schema(description = "库存报警日志action")
		@NotBlank(message = "库存报警日志action 不能为空", groups = UpdateGroup.class)
			private String action;

	/**
	 * 报警时间
	 */
	@Schema(description = "库存报警日志alertDate")
			@NotNull(message = "库存报警日志alertDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime alertDate;

	/**
	 * 报警状态（处理状态）
	 */
	@Schema(description = "库存报警日志status")
		@NotBlank(message = "库存报警日志status 不能为空", groups = UpdateGroup.class)
			private String status;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存报警日志createTime")
			@NotNull(message = "库存报警日志createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存报警日志creatorId")
		@NotBlank(message = "库存报警日志creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 更新时间
	 */
	@Schema(description = "库存报警日志updateTime")
			@NotNull(message = "库存报警日志updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 更新人id
	 */
	@Schema(description = "库存报警日志updatorId")
		@NotBlank(message = "库存报警日志updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识 0-未删除；1-已删除
	 */
	@Schema(description = "库存报警日志deleted")
			@NotNull(message = "库存报警日志deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存报警日志tenantId")
		@NotBlank(message = "库存报警日志tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
