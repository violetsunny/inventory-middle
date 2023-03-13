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
 * 库存-在途Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存-在途", description = "库存-在途")
public class InventoryTransitCommand extends Command {

	/**
	 * 在途id
	 */
	@Schema(description = "库存-在途id")
			@NotNull(message = "库存-在途id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料code
	 */
	@Schema(description = "库存-在途materialCode")
		@NotBlank(message = "库存-在途materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 逻辑仓库no
	 */
	@Schema(description = "库存-在途logicalPlantNo")
		@NotBlank(message = "库存-在途logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 存储地点no
	 */
	@Schema(description = "库存-在途storageLocationNo")
		@NotBlank(message = "库存-在途storageLocationNo 不能为空", groups = UpdateGroup.class)
			private String storageLocationNo;

	/**
	 * 良品数量
	 */
	@Schema(description = "库存-在途unrestricted")
			@NotNull(message = "库存-在途unrestricted 不能为空", groups = UpdateGroup.class)
		private BigDecimal unrestricted;

	/**
	 * 次品数量
	 */
	@Schema(description = "库存-在途damaged")
			@NotNull(message = "库存-在途damaged 不能为空", groups = UpdateGroup.class)
		private BigDecimal damaged;

	/**
	 * 质检数量
	 */
	@Schema(description = "库存-在途inspection")
			@NotNull(message = "库存-在途inspection 不能为空", groups = UpdateGroup.class)
		private BigDecimal inspection;

	/**
	 * 计量单位
	 */
	@Schema(description = "库存-在途uom")
		@NotBlank(message = "库存-在途uom 不能为空", groups = UpdateGroup.class)
			private String uom;

	/**
	 * 生产日期
	 */
	@Schema(description = "库存-在途productDate")
			@NotNull(message = "库存-在途productDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime productDate;

	/**
	 * 过期日期
	 */
	@Schema(description = "库存-在途dueDate")
			@NotNull(message = "库存-在途dueDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime dueDate;

	/**
	 * 价格
	 */
	@Schema(description = "库存-在途price")
			@NotNull(message = "库存-在途price 不能为空", groups = UpdateGroup.class)
		private BigDecimal price;

	/**
	 * 货币单位
	 */
	@Schema(description = "库存-在途currency")
		@NotBlank(message = "库存-在途currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 在途类型1-入库在途 2-出库在途
	 */
	@Schema(description = "库存-在途transitType")
			@NotNull(message = "库存-在途transitType 不能为空", groups = UpdateGroup.class)
		private Integer transitType;

	/**
	 * 交运单编号
	 */
	@Schema(description = "库存-在途deliveryNo")
		@NotBlank(message = "库存-在途deliveryNo 不能为空", groups = UpdateGroup.class)
			private String deliveryNo;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存-在途createTime")
			@NotNull(message = "库存-在途createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存-在途creatorId")
		@NotBlank(message = "库存-在途creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "库存-在途updateTime")
			@NotNull(message = "库存-在途updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "库存-在途updatorId")
		@NotBlank(message = "库存-在途updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "库存-在途deleted")
			@NotNull(message = "库存-在途deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存-在途tenantId")
		@NotBlank(message = "库存-在途tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
