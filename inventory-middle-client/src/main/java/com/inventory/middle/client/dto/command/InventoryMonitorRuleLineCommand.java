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
 * 库存预警规则明细Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存预警规则明细", description = "库存预警规则明细")
public class InventoryMonitorRuleLineCommand extends Command {

	/**
	 * 规则明细id
	 */
	@Schema(description = "库存预警规则明细id")
			@NotNull(message = "库存预警规则明细id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 预警规则id
	 */
	@Schema(description = "库存预警规则明细monitorRuleId")
			@NotNull(message = "库存预警规则明细monitorRuleId 不能为空", groups = UpdateGroup.class)
		private Long monitorRuleId;

	/**
	 * 预警维度
	 */
	@Schema(description = "库存预警规则明细monitorDimension")
		@NotBlank(message = "库存预警规则明细monitorDimension 不能为空", groups = UpdateGroup.class)
			private String monitorDimension;

	/**
	 * 预警对象
	 */
	@Schema(description = "库存预警规则明细monitorObject")
		@NotBlank(message = "库存预警规则明细monitorObject 不能为空", groups = UpdateGroup.class)
			private String monitorObject;

	/**
	 * 上限
	 */
	@Schema(description = "库存预警规则明细monitorCeil")
			@NotNull(message = "库存预警规则明细monitorCeil 不能为空", groups = UpdateGroup.class)
		private BigDecimal monitorCeil;

	/**
	 * 下限
	 */
	@Schema(description = "库存预警规则明细monitorFloor")
			@NotNull(message = "库存预警规则明细monitorFloor 不能为空", groups = UpdateGroup.class)
		private BigDecimal monitorFloor;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存预警规则明细createTime")
			@NotNull(message = "库存预警规则明细createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存预警规则明细creatorId")
			@NotNull(message = "库存预警规则明细creatorId 不能为空", groups = UpdateGroup.class)
		private Long creatorId;

	/**
	 * 更新时间
	 */
	@Schema(description = "库存预警规则明细updateTime")
			@NotNull(message = "库存预警规则明细updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 更新人id
	 */
	@Schema(description = "库存预警规则明细updatorId")
			@NotNull(message = "库存预警规则明细updatorId 不能为空", groups = UpdateGroup.class)
		private Long updatorId;

	/**
	 * 删除标识 0-未删除；1-已删除
	 */
	@Schema(description = "库存预警规则明细deleted")
			@NotNull(message = "库存预警规则明细deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存预警规则明细tenantId")
		@NotBlank(message = "库存预警规则明细tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
