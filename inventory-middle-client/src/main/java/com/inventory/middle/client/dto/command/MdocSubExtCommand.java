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
 * 物料凭证-标签-扩展Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证-标签-扩展", description = "物料凭证-标签-扩展")
public class MdocSubExtCommand extends Command {

	/**
	 * id
	 */
	@Schema(description = "物料凭证-标签-扩展id")
			@NotNull(message = "物料凭证-标签-扩展id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证id
	 */
	@Schema(description = "物料凭证-标签-扩展materialDocId")
			@NotNull(message = "物料凭证-标签-扩展materialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料凭证itemId
	 */
	@Schema(description = "物料凭证-标签-扩展materialDocItemId")
			@NotNull(message = "物料凭证-标签-扩展materialDocItemId 不能为空", groups = UpdateGroup.class)
		private Long materialDocItemId;

	/**
	 * 批次有效天数
	 */
	@Schema(description = "物料凭证-标签-扩展validDays")
			@NotNull(message = "物料凭证-标签-扩展validDays 不能为空", groups = UpdateGroup.class)
		private Integer validDays;

	/**
	 * 生产日期
	 */
	@Schema(description = "物料凭证-标签-扩展produceDate")
			@NotNull(message = "物料凭证-标签-扩展produceDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime produceDate;

	/**
	 * 海关编码
	 */
	@Schema(description = "物料凭证-标签-扩展hsCode")
		@NotBlank(message = "物料凭证-标签-扩展hsCode 不能为空", groups = UpdateGroup.class)
			private String hsCode;

	/**
	 * 下次年检日期yyyy-mm-dd
	 */
	@Schema(description = "物料凭证-标签-扩展annualDate")
			@NotNull(message = "物料凭证-标签-扩展annualDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime annualDate;

	/**
	 * 年检周期天数
	 */
	@Schema(description = "物料凭证-标签-扩展annualCycleDays")
			@NotNull(message = "物料凭证-标签-扩展annualCycleDays 不能为空", groups = UpdateGroup.class)
		private Integer annualCycleDays;

	/**
	 * 租户id
	 */
	@Schema(description = "物料凭证-标签-扩展tenantId")
		@NotBlank(message = "物料凭证-标签-扩展tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 创建人id
	 */
	@Schema(description = "物料凭证-标签-扩展creatorId")
			@NotNull(message = "物料凭证-标签-扩展creatorId 不能为空", groups = UpdateGroup.class)
		private Long creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证-标签-扩展updateTime")
			@NotNull(message = "物料凭证-标签-扩展updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "物料凭证-标签-扩展updatorId")
			@NotNull(message = "物料凭证-标签-扩展updatorId 不能为空", groups = UpdateGroup.class)
		private Long updatorId;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证-标签-扩展createTime")
			@NotNull(message = "物料凭证-标签-扩展createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证-标签-扩展deleted")
			@NotNull(message = "物料凭证-标签-扩展deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

}
