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
 * 库存-计划Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存-计划", description = "库存-计划")
public class InventoryPlanCommand extends Command {

	/**
	 * 在途id
	 */
	@Schema(description = "库存-计划id")
			@NotNull(message = "库存-计划id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料code
	 */
	@Schema(description = "库存-计划materialCode")
		@NotBlank(message = "库存-计划materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 逻辑仓库no
	 */
	@Schema(description = "库存-计划logicalPlantNo")
		@NotBlank(message = "库存-计划logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 存储地点no
	 */
	@Schema(description = "库存-计划storageLocationNo")
		@NotBlank(message = "库存-计划storageLocationNo 不能为空", groups = UpdateGroup.class)
			private String storageLocationNo;

	/**
	 * 良品数量
	 */
	@Schema(description = "库存-计划unrestricted")
			@NotNull(message = "库存-计划unrestricted 不能为空", groups = UpdateGroup.class)
		private BigDecimal unrestricted;

	/**
	 * 次品数量
	 */
	@Schema(description = "库存-计划damaged")
			@NotNull(message = "库存-计划damaged 不能为空", groups = UpdateGroup.class)
		private BigDecimal damaged;

	/**
	 * 质检数量
	 */
	@Schema(description = "库存-计划inspection")
			@NotNull(message = "库存-计划inspection 不能为空", groups = UpdateGroup.class)
		private BigDecimal inspection;

	/**
	 * 计量单位
	 */
	@Schema(description = "库存-计划uom")
		@NotBlank(message = "库存-计划uom 不能为空", groups = UpdateGroup.class)
			private String uom;

	/**
	 * 生产日期
	 */
	@Schema(description = "库存-计划productDate")
			@NotNull(message = "库存-计划productDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime productDate;

	/**
	 * 过期日期
	 */
	@Schema(description = "库存-计划dueDate")
			@NotNull(message = "库存-计划dueDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime dueDate;

	/**
	 * 价格
	 */
	@Schema(description = "库存-计划price")
			@NotNull(message = "库存-计划price 不能为空", groups = UpdateGroup.class)
		private BigDecimal price;

	/**
	 * 货币单位
	 */
	@Schema(description = "库存-计划currency")
		@NotBlank(message = "库存-计划currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 在途类型1-计划入库 2-计划出库
	 */
	@Schema(description = "库存-计划planType")
			@NotNull(message = "库存-计划planType 不能为空", groups = UpdateGroup.class)
		private Integer planType;

	/**
	 * 计划编号
	 */
	@Schema(description = "库存-计划planNo")
		@NotBlank(message = "库存-计划planNo 不能为空", groups = UpdateGroup.class)
			private String planNo;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存-计划createTime")
			@NotNull(message = "库存-计划createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存-计划creatorId")
		@NotBlank(message = "库存-计划creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "库存-计划updateTime")
			@NotNull(message = "库存-计划updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "库存-计划updatorId")
		@NotBlank(message = "库存-计划updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "库存-计划deleted")
			@NotNull(message = "库存-计划deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存-计划tenantId")
		@NotBlank(message = "库存-计划tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
