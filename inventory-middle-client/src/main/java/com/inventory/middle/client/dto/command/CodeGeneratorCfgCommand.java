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
 * 流水号配置表Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "流水号配置表", description = "流水号配置表")
public class CodeGeneratorCfgCommand extends Command {

	/**
	 * 主键(自增处理)
	 */
	@Schema(description = "流水号配置表id")
			@NotNull(message = "流水号配置表id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 流水号唯一code
	 */
	@Schema(description = "流水号配置表code")
		@NotBlank(message = "流水号配置表code 不能为空", groups = UpdateGroup.class)
			private String code;

	/**
	 * 流水号名称
	 */
	@Schema(description = "流水号配置表name")
		@NotBlank(message = "流水号配置表name 不能为空", groups = UpdateGroup.class)
			private String name;

	/**
	 * 当前编号池的纪元
	 */
	@Schema(description = "流水号配置表epoch")
			@NotNull(message = "流水号配置表epoch 不能为空", groups = UpdateGroup.class)
		private Long epoch;

	/**
	 * 最大流水号
	 */
	@Schema(description = "流水号配置表maxValue")
		@NotBlank(message = "流水号配置表maxValue 不能为空", groups = UpdateGroup.class)
			private String maxValue;

	/**
	 * 流水号规则
	 */
	@Schema(description = "流水号配置表rule")
		@NotBlank(message = "流水号配置表rule 不能为空", groups = UpdateGroup.class)
			private String rule;

	/**
	 * 是否缓存 0：否  ， 1：是
	 */
	@Schema(description = "流水号配置表isCache")
			@NotNull(message = "流水号配置表isCache 不能为空", groups = UpdateGroup.class)
		private Integer isCache;

	/**
	 * 缓存数目
	 */
	@Schema(description = "流水号配置表cacheNum")
			@NotNull(message = "流水号配置表cacheNum 不能为空", groups = UpdateGroup.class)
		private Integer cacheNum;

	/**
	 * 环境变量值
	 */
	@Schema(description = "流水号配置表envValue")
		@NotBlank(message = "流水号配置表envValue 不能为空", groups = UpdateGroup.class)
			private String envValue;

	/**
	 * 状态：0有效 1无效
	 */
	@Schema(description = "流水号配置表isDeleted")
			@NotNull(message = "流水号配置表isDeleted 不能为空", groups = UpdateGroup.class)
		private Integer isDeleted;

	/**
	 * 备注
	 */
	@Schema(description = "流水号配置表remark")
		@NotBlank(message = "流水号配置表remark 不能为空", groups = UpdateGroup.class)
			private String remark;

	/**
	 * 创建时间
	 */
	@Schema(description = "流水号配置表createTime")
			@NotNull(message = "流水号配置表createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "流水号配置表updateTime")
			@NotNull(message = "流水号配置表updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

}
