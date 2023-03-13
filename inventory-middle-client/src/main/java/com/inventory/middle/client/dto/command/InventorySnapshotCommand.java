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
 * 库存快照-实时Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存快照-实时", description = "库存快照-实时")
public class InventorySnapshotCommand extends Command {

	/**
	 * 库存快照主键
	 */
	@Schema(description = "库存快照-实时id")
			@NotNull(message = "库存快照-实时id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料code
	 */
	@Schema(description = "库存快照-实时materialCode")
		@NotBlank(message = "库存快照-实时materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 物料类目
	 */
	@Schema(description = "库存快照-实时materialCategoryCode")
		@NotBlank(message = "库存快照-实时materialCategoryCode 不能为空", groups = UpdateGroup.class)
			private String materialCategoryCode;

	/**
	 * 逻辑仓库no
	 */
	@Schema(description = "库存快照-实时logicalPlantNo")
		@NotBlank(message = "库存快照-实时logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 存储地点no
	 */
	@Schema(description = "库存快照-实时storageLocationNo")
		@NotBlank(message = "库存快照-实时storageLocationNo 不能为空", groups = UpdateGroup.class)
			private String storageLocationNo;

	/**
	 * 批次号
	 */
	@Schema(description = "库存快照-实时batchNo")
		@NotBlank(message = "库存快照-实时batchNo 不能为空", groups = UpdateGroup.class)
			private String batchNo;

	/**
	 * 批次入库时间
	 */
	@Schema(description = "库存快照-实时batchTime")
			@NotNull(message = "库存快照-实时batchTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime batchTime;

	/**
	 * 批次价格
	 */
	@Schema(description = "库存快照-实时batchPrice")
			@NotNull(message = "库存快照-实时batchPrice 不能为空", groups = UpdateGroup.class)
		private BigDecimal batchPrice;

	/**
	 * 货币
	 */
	@Schema(description = "库存快照-实时currency")
		@NotBlank(message = "库存快照-实时currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 正品库存
	 */
	@Schema(description = "库存快照-实时unrestricted")
			@NotNull(message = "库存快照-实时unrestricted 不能为空", groups = UpdateGroup.class)
		private BigDecimal unrestricted;

	/**
	 * 次品库存
	 */
	@Schema(description = "库存快照-实时damaged")
			@NotNull(message = "库存快照-实时damaged 不能为空", groups = UpdateGroup.class)
		private BigDecimal damaged;

	/**
	 * 质检库存
	 */
	@Schema(description = "库存快照-实时inspection")
			@NotNull(message = "库存快照-实时inspection 不能为空", groups = UpdateGroup.class)
		private BigDecimal inspection;

	/**
	 * 计量单位
	 */
	@Schema(description = "库存快照-实时uom")
		@NotBlank(message = "库存快照-实时uom 不能为空", groups = UpdateGroup.class)
			private String uom;

	/**
	 * 生产日期
	 */
	@Schema(description = "库存快照-实时productDate")
			@NotNull(message = "库存快照-实时productDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime productDate;

	/**
	 * 过期日期
	 */
	@Schema(description = "库存快照-实时dueDate")
			@NotNull(message = "库存快照-实时dueDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime dueDate;

	/**
	 * 库存所有者
	 */
	@Schema(description = "库存快照-实时owner")
		@NotBlank(message = "库存快照-实时owner 不能为空", groups = UpdateGroup.class)
			private String owner;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存快照-实时createTime")
			@NotNull(message = "库存快照-实时createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人ID
	 */
	@Schema(description = "库存快照-实时creatorId")
		@NotBlank(message = "库存快照-实时creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "库存快照-实时updateTime")
			@NotNull(message = "库存快照-实时updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人ID
	 */
	@Schema(description = "库存快照-实时updatorId")
		@NotBlank(message = "库存快照-实时updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "库存快照-实时deleted")
			@NotNull(message = "库存快照-实时deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户ID
	 */
	@Schema(description = "库存快照-实时tenantId")
		@NotBlank(message = "库存快照-实时tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
