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
 * 物料凭证-标签-移动平均价Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证-标签-移动平均价", description = "物料凭证-标签-移动平均价")
public class MdocSubMapCommand extends Command {

	/**
	 * id
	 */
	@Schema(description = "物料凭证-标签-移动平均价id")
			@NotNull(message = "物料凭证-标签-移动平均价id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证id
	 */
	@Schema(description = "物料凭证-标签-移动平均价materialDocId")
			@NotNull(message = "物料凭证-标签-移动平均价materialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料凭证itemId
	 */
	@Schema(description = "物料凭证-标签-移动平均价materialDocItemId")
			@NotNull(message = "物料凭证-标签-移动平均价materialDocItemId 不能为空", groups = UpdateGroup.class)
		private Long materialDocItemId;

	/**
	 * map流水code
	 */
	@Schema(description = "物料凭证-标签-移动平均价mapCode")
		@NotBlank(message = "物料凭证-标签-移动平均价mapCode 不能为空", groups = UpdateGroup.class)
			private String mapCode;

	/**
	 * map子流水号
	 */
	@Schema(description = "物料凭证-标签-移动平均价mapSubCode")
		@NotBlank(message = "物料凭证-标签-移动平均价mapSubCode 不能为空", groups = UpdateGroup.class)
			private String mapSubCode;

	/**
	 * map流水状态
	 */
	@Schema(description = "物料凭证-标签-移动平均价mapStatus")
			@NotNull(message = "物料凭证-标签-移动平均价mapStatus 不能为空", groups = UpdateGroup.class)
		private Integer mapStatus;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证-标签-移动平均价deleted")
			@NotNull(message = "物料凭证-标签-移动平均价deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "物料凭证-标签-移动平均价tenantId")
		@NotBlank(message = "物料凭证-标签-移动平均价tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 创建人id
	 */
	@Schema(description = "物料凭证-标签-移动平均价creatorId")
			@NotNull(message = "物料凭证-标签-移动平均价creatorId 不能为空", groups = UpdateGroup.class)
		private Long creatorId;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证-标签-移动平均价createTime")
			@NotNull(message = "物料凭证-标签-移动平均价createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证-标签-移动平均价updateTime")
			@NotNull(message = "物料凭证-标签-移动平均价updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "物料凭证-标签-移动平均价updatorId")
			@NotNull(message = "物料凭证-标签-移动平均价updatorId 不能为空", groups = UpdateGroup.class)
		private Long updatorId;

}
