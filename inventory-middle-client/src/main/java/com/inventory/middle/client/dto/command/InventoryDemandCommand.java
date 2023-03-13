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
 * 库存-需求Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "库存-需求", description = "库存-需求")
public class InventoryDemandCommand extends Command {

	/**
	 * 需求id
	 */
	@Schema(description = "库存-需求id")
			@NotNull(message = "库存-需求id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料code
	 */
	@Schema(description = "库存-需求materialCode")
		@NotBlank(message = "库存-需求materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 批次号
	 */
	@Schema(description = "库存-需求batchNo")
		@NotBlank(message = "库存-需求batchNo 不能为空", groups = UpdateGroup.class)
			private String batchNo;

	/**
	 * 批次出库时间
	 */
	@Schema(description = "库存-需求batchTime")
			@NotNull(message = "库存-需求batchTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime batchTime;

	/**
	 * 库存所有者
	 */
	@Schema(description = "库存-需求owner")
		@NotBlank(message = "库存-需求owner 不能为空", groups = UpdateGroup.class)
			private String owner;

	/**
	 * 所在仓库
	 */
	@Schema(description = "库存-需求logicalPlantNo")
		@NotBlank(message = "库存-需求logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 存储地点
	 */
	@Schema(description = "库存-需求storageLocationNo")
		@NotBlank(message = "库存-需求storageLocationNo 不能为空", groups = UpdateGroup.class)
			private String storageLocationNo;

	/**
	 * 正品数量
	 */
	@Schema(description = "库存-需求unrestricted")
			@NotNull(message = "库存-需求unrestricted 不能为空", groups = UpdateGroup.class)
		private BigDecimal unrestricted;

	/**
	 * 次品数量
	 */
	@Schema(description = "库存-需求damaged")
			@NotNull(message = "库存-需求damaged 不能为空", groups = UpdateGroup.class)
		private BigDecimal damaged;

	/**
	 * 质检数量
	 */
	@Schema(description = "库存-需求inspection")
			@NotNull(message = "库存-需求inspection 不能为空", groups = UpdateGroup.class)
		private BigDecimal inspection;

	/**
	 * 计量单位
	 */
	@Schema(description = "库存-需求uom")
		@NotBlank(message = "库存-需求uom 不能为空", groups = UpdateGroup.class)
			private String uom;

	/**
	 * 需求取消日期
	 */
	@Schema(description = "库存-需求cancelDate")
			@NotNull(message = "库存-需求cancelDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime cancelDate;

	/**
	 * 需求过期日期
	 */
	@Schema(description = "库存-需求deadlineDate")
			@NotNull(message = "库存-需求deadlineDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime deadlineDate;

	/**
	 * 货币单位
	 */
	@Schema(description = "库存-需求currency")
		@NotBlank(message = "库存-需求currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 需求类型1-立即出库，2-在途转出库，3-计划转出库
	 */
	@Schema(description = "库存-需求demandType")
			@NotNull(message = "库存-需求demandType 不能为空", groups = UpdateGroup.class)
		private Integer demandType;

	/**
	 * 出库价
	 */
	@Schema(description = "库存-需求demandPrice")
			@NotNull(message = "库存-需求demandPrice 不能为空", groups = UpdateGroup.class)
		private BigDecimal demandPrice;

	/**
	 * 创建时间
	 */
	@Schema(description = "库存-需求createTime")
			@NotNull(message = "库存-需求createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "库存-需求creatorId")
		@NotBlank(message = "库存-需求creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "库存-需求updateTime")
			@NotNull(message = "库存-需求updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "库存-需求updatorId")
		@NotBlank(message = "库存-需求updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "库存-需求deleted")
			@NotNull(message = "库存-需求deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "库存-需求tenantId")
		@NotBlank(message = "库存-需求tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
