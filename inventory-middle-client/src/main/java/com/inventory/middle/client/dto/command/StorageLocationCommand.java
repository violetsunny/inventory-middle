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
 * 存储地点表Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "存储地点表", description = "存储地点表")
public class StorageLocationCommand extends Command {

	/**
	 * 主键ID
	 */
	@Schema(description = "存储地点表id")
			@NotNull(message = "存储地点表id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 创建该存储地点的租户id
	 */
	@Schema(description = "存储地点表tenantId")
		@NotBlank(message = "存储地点表tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 存储地点编码
	 */
	@Schema(description = "存储地点表storageLocationNo")
		@NotBlank(message = "存储地点表storageLocationNo 不能为空", groups = UpdateGroup.class)
			private String storageLocationNo;

	/**
	 * 存储地点名称
	 */
	@Schema(description = "存储地点表storageLocationName")
		@NotBlank(message = "存储地点表storageLocationName 不能为空", groups = UpdateGroup.class)
			private String storageLocationName;

	/**
	 * 逻辑仓库no
	 */
	@Schema(description = "存储地点表logicalPlantNo")
			@NotNull(message = "存储地点表logicalPlantNo 不能为空", groups = UpdateGroup.class)
		private Long logicalPlantNo;

	/**
	 * 存储地点类型 0-普通存储地点；1-客户寄售；2-待回收包装；3-委外加工物资；4-供应商寄售；5-待退回包装；6-销售订单
	 */
	@Schema(description = "存储地点表locationType")
			@NotNull(message = "存储地点表locationType 不能为空", groups = UpdateGroup.class)
		private Integer locationType;

	/**
	 * 描述
	 */
	@Schema(description = "存储地点表description")
		@NotBlank(message = "存储地点表description 不能为空", groups = UpdateGroup.class)
			private String description;

	/**
	 * 存储地点位置
	 */
	@Schema(description = "存储地点表position")
		@NotBlank(message = "存储地点表position 不能为空", groups = UpdateGroup.class)
			private String position;

	/**
	 * 创建人
	 */
	@Schema(description = "存储地点表creatorId")
		@NotBlank(message = "存储地点表creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 更新人
	 */
	@Schema(description = "存储地点表updatorId")
		@NotBlank(message = "存储地点表updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 创建时间
	 */
	@Schema(description = "存储地点表createTime")
			@NotNull(message = "存储地点表createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "存储地点表updateTime")
			@NotNull(message = "存储地点表updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	@Schema(description = "存储地点表deleted")
			@NotNull(message = "存储地点表deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

}
