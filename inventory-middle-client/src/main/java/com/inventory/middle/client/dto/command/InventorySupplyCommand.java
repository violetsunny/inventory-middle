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
 * 库存-供给Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存-供给", description = "库存-供给")
public class InventorySupplyCommand extends Command {

	/**
	 * 供应id
	 */
	@Schema(description = "库存-供给id")
			@NotNull(message = "库存-供给id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料code
	 */
	@Schema(description = "库存-供给materialCode")
		@NotBlank(message = "库存-供给materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 批次号
	 */
	@Schema(description = "库存-供给batchNo")
		@NotBlank(message = "库存-供给batchNo 不能为空", groups = UpdateGroup.class)
			private String batchNo;

	/**
	 * 批次入库时间
	 */
	@Schema(description = "库存-供给batchTime")
			@NotNull(message = "库存-供给batchTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime batchTime;

	/**
	 * 库存所有者
	 */
	@Schema(description = "库存-供给owner")
		@NotBlank(message = "库存-供给owner 不能为空", groups = UpdateGroup.class)
			private String owner;

	/**
	 * 所在仓库
	 */
	@Schema(description = "库存-供给logicalPlantNo")
		@NotBlank(message = "库存-供给logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 存储地点
	 */
	@Schema(description = "库存-供给storageLocationNo")
		@NotBlank(message = "库存-供给storageLocationNo 不能为空", groups = UpdateGroup.class)
			private String storageLocationNo;

	/**
	 * 良品数量
	 */
	@Schema(description = "库存-供给unrestricted")
			@NotNull(message = "库存-供给unrestricted 不能为空", groups = UpdateGroup.class)
		private BigDecimal unrestricted;

	/**
	 * 次品数量
	 */
	@Schema(description = "库存-供给damaged")
			@NotNull(message = "库存-供给damaged 不能为空", groups = UpdateGroup.class)
		private BigDecimal damaged;

	/**
	 * 质检数量
	 */
	@Schema(description = "库存-供给inspection")
			@NotNull(message = "库存-供给inspection 不能为空", groups = UpdateGroup.class)
		private BigDecimal inspection;

	/**
	 * 计量单位
	 */
	@Schema(description = "库存-供给uom")
		@NotBlank(message = "库存-供给uom 不能为空", groups = UpdateGroup.class)
			private String uom;

	/**
	 * 生产日期
	 */
	@Schema(description = "库存-供给productDate")
			@NotNull(message = "库存-供给productDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime productDate;

	/**
	 * 最早可用日期
	 */
	@Schema(description = "库存-供给availableDate")
			@NotNull(message = "库存-供给availableDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime availableDate;

	/**
	 * 过期日期
	 */
	@Schema(description = "库存-供给dueDate")
			@NotNull(message = "库存-供给dueDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime dueDate;

	/**
	 * 批次价格
	 */
	@Schema(description = "库存-供给batchPrice")
			@NotNull(message = "库存-供给batchPrice 不能为空", groups = UpdateGroup.class)
		private BigDecimal batchPrice;

	/**
	 * 货币单位
	 */
	@Schema(description = "库存-供给currency")
		@NotBlank(message = "库存-供给currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 供应类型1-立即入库，2-在途转入库，3-计划转入库
	 */
	@Schema(description = "库存-供给supplyType")
			@NotNull(message = "库存-供给supplyType 不能为空", groups = UpdateGroup.class)
		private Integer supplyType;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存-供给createTime")
			@NotNull(message = "库存-供给createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存-供给creatorId")
		@NotBlank(message = "库存-供给creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "库存-供给updateTime")
			@NotNull(message = "库存-供给updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "库存-供给updatorId")
		@NotBlank(message = "库存-供给updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "库存-供给deleted")
			@NotNull(message = "库存-供给deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存-供给tenantId")
		@NotBlank(message = "库存-供给tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
