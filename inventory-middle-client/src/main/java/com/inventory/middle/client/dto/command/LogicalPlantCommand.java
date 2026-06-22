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
 * 逻辑仓库表Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "逻辑仓库表", description = "逻辑仓库表")
public class LogicalPlantCommand extends Command {

	/**
	 * 主键ID
	 */
	@Schema(description = "逻辑仓库表id")
			@NotNull(message = "逻辑仓库表id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 创建该逻辑仓库的租户id
	 */
	@Schema(description = "逻辑仓库表tenantId")
		@NotBlank(message = "逻辑仓库表tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 逻辑仓库编码
	 */
	@Schema(description = "逻辑仓库表logicalPlantNo")
		@NotBlank(message = "逻辑仓库表logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 逻辑仓库名称
	 */
	@Schema(description = "逻辑仓库表logicalPlantName")
		@NotBlank(message = "逻辑仓库表logicalPlantName 不能为空", groups = UpdateGroup.class)
			private String logicalPlantName;

	/**
	 * 逻辑仓库类型
	 */
	@Schema(description = "逻辑仓库表type")
			@NotNull(message = "逻辑仓库表type 不能为空", groups = UpdateGroup.class)
		private Integer type;

	/**
	 * 公司主体代码
	 */
	@Schema(description = "逻辑仓库表companyCode")
		@NotBlank(message = "逻辑仓库表companyCode 不能为空", groups = UpdateGroup.class)
			private String companyCode;

	/**
	 * 该逻辑仓所属的物理仓no
	 */
	@Schema(description = "逻辑仓库表warehouseNo")
		@NotBlank(message = "逻辑仓库表warehouseNo 不能为空", groups = UpdateGroup.class)
			private String warehouseNo;

	/**
	 * 省
	 */
	@Schema(description = "逻辑仓库表province")
		@NotBlank(message = "逻辑仓库表province 不能为空", groups = UpdateGroup.class)
			private String province;

	/**
	 * 市
	 */
	@Schema(description = "逻辑仓库表city")
		@NotBlank(message = "逻辑仓库表city 不能为空", groups = UpdateGroup.class)
			private String city;

	/**
	 * 区
	 */
	@Schema(description = "逻辑仓库表region")
		@NotBlank(message = "逻辑仓库表region 不能为空", groups = UpdateGroup.class)
			private String region;

	/**
	 * 逻辑仓库地址
	 */
	@Schema(description = "逻辑仓库表address")
		@NotBlank(message = "逻辑仓库表address 不能为空", groups = UpdateGroup.class)
			private String address;

	/**
	 * 创建人
	 */
	@Schema(description = "逻辑仓库表creatorId")
		@NotBlank(message = "逻辑仓库表creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 更新人
	 */
	@Schema(description = "逻辑仓库表updatorId")
		@NotBlank(message = "逻辑仓库表updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 创建时间
	 */
	@Schema(description = "逻辑仓库表createTime")
			@NotNull(message = "逻辑仓库表createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "逻辑仓库表updateTime")
			@NotNull(message = "逻辑仓库表updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	@Schema(description = "逻辑仓库表deleted")
			@NotNull(message = "逻辑仓库表deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

}
