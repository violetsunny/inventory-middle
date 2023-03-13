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
 * 移动平均价Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "移动平均价", description = "移动平均价")
public class InventoryMapCommand extends Command {

	/**
	 * MAP主键
	 */
	@Schema(description = "移动平均价id")
			@NotNull(message = "移动平均价id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * map流水号
	 */
	@Schema(description = "移动平均价mapCode")
		@NotBlank(message = "移动平均价mapCode 不能为空", groups = UpdateGroup.class)
			private String mapCode;

	/**
	 * map子流水code
	 */
	@Schema(description = "移动平均价mapSubCode")
		@NotBlank(message = "移动平均价mapSubCode 不能为空", groups = UpdateGroup.class)
			private String mapSubCode;

	/**
	 * 物料code
	 */
	@Schema(description = "移动平均价skuCode")
		@NotBlank(message = "移动平均价skuCode 不能为空", groups = UpdateGroup.class)
			private String skuCode;

	/**
	 * 逻辑仓
	 */
	@Schema(description = "移动平均价logicalPlantNo")
		@NotBlank(message = "移动平均价logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 移动平均价
	 */
	@Schema(description = "移动平均价map")
			@NotNull(message = "移动平均价map 不能为空", groups = UpdateGroup.class)
		private BigDecimal map;

	/**
	 * 创建时间
	 */
	@Schema(description = "移动平均价createTime")
			@NotNull(message = "移动平均价createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人ID
	 */
	@Schema(description = "移动平均价creatorId")
		@NotBlank(message = "移动平均价creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "移动平均价updateTime")
			@NotNull(message = "移动平均价updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人ID
	 */
	@Schema(description = "移动平均价updatorId")
		@NotBlank(message = "移动平均价updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "移动平均价deleted")
			@NotNull(message = "移动平均价deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户ID
	 */
	@Schema(description = "移动平均价tenantId")
		@NotBlank(message = "移动平均价tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
