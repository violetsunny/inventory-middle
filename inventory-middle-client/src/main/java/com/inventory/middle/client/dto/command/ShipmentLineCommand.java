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
 * 交运单明细Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "交运单明细", description = "交运单明细")
public class ShipmentLineCommand extends Command {

	/**
	 * 交运单明细id
	 */
	@Schema(description = "交运单明细id")
			@NotNull(message = "交运单明细id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 来源单据编号
	 */
	@Schema(description = "交运单明细sourceOrderNo")
		@NotBlank(message = "交运单明细sourceOrderNo 不能为空", groups = UpdateGroup.class)
			private String sourceOrderNo;

	/**
	 * 来源单号类型
	 */
	@Schema(description = "交运单明细sourceOrderType")
		@NotBlank(message = "交运单明细sourceOrderType 不能为空", groups = UpdateGroup.class)
			private String sourceOrderType;

	/**
	 * 交运单id
	 */
	@Schema(description = "交运单明细shipmentId")
			@NotNull(message = "交运单明细shipmentId 不能为空", groups = UpdateGroup.class)
		private Long shipmentId;

	/**
	 * 交运单号
	 */
	@Schema(description = "交运单明细shipmentNo")
		@NotBlank(message = "交运单明细shipmentNo 不能为空", groups = UpdateGroup.class)
			private String shipmentNo;

	/**
	 * 物料编码
	 */
	@Schema(description = "交运单明细materialCode")
		@NotBlank(message = "交运单明细materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 外部编码
	 */
	@Schema(description = "交运单明细externalCode")
		@NotBlank(message = "交运单明细externalCode 不能为空", groups = UpdateGroup.class)
			private String externalCode;

	/**
	 * 交运数量
	 */
	@Schema(description = "交运单明细quantity")
			@NotNull(message = "交运单明细quantity 不能为空", groups = UpdateGroup.class)
		private BigDecimal quantity;

	/**
	 * 实际交运数量
	 */
	@Schema(description = "交运单明细actualQuantity")
			@NotNull(message = "交运单明细actualQuantity 不能为空", groups = UpdateGroup.class)
		private BigDecimal actualQuantity;

	/**
	 * 已开票数量
	 */
	@Schema(description = "交运单明细invoicedQuantity")
			@NotNull(message = "交运单明细invoicedQuantity 不能为空", groups = UpdateGroup.class)
		private BigDecimal invoicedQuantity;

	/**
	 * 成本中心
	 */
	@Schema(description = "交运单明细costCenterCode")
		@NotBlank(message = "交运单明细costCenterCode 不能为空", groups = UpdateGroup.class)
			private String costCenterCode;

	/**
	 * 会计科目
	 */
	@Schema(description = "交运单明细accountingCode")
		@NotBlank(message = "交运单明细accountingCode 不能为空", groups = UpdateGroup.class)
			private String accountingCode;

	/**
	 * 收货逻辑仓库no
	 */
	@Schema(description = "交运单明细receivingLogicalPlantNo")
		@NotBlank(message = "交运单明细receivingLogicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String receivingLogicalPlantNo;

	/**
	 * 收货物理仓库no
	 */
	@Schema(description = "交运单明细receivingWarehouseNo")
		@NotBlank(message = "交运单明细receivingWarehouseNo 不能为空", groups = UpdateGroup.class)
			private String receivingWarehouseNo;

	/**
	 * 收货存储位置no
	 */
	@Schema(description = "交运单明细receivingStorageLocationNo")
		@NotBlank(message = "交运单明细receivingStorageLocationNo 不能为空", groups = UpdateGroup.class)
			private String receivingStorageLocationNo;

	/**
	 * 收货批次号
	 */
	@Schema(description = "交运单明细receivingBatchNo")
		@NotBlank(message = "交运单明细receivingBatchNo 不能为空", groups = UpdateGroup.class)
			private String receivingBatchNo;

	/**
	 * 收货公司主体
	 */
	@Schema(description = "交运单明细receivingCompanyCode")
		@NotBlank(message = "交运单明细receivingCompanyCode 不能为空", groups = UpdateGroup.class)
			private String receivingCompanyCode;

	/**
	 * 发货逻辑仓库no
	 */
	@Schema(description = "交运单明细deliveryLogicalPlantNo")
		@NotBlank(message = "交运单明细deliveryLogicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String deliveryLogicalPlantNo;

	/**
	 * 发货物理仓库no
	 */
	@Schema(description = "交运单明细deliveryWarehouseNo")
		@NotBlank(message = "交运单明细deliveryWarehouseNo 不能为空", groups = UpdateGroup.class)
			private String deliveryWarehouseNo;

	/**
	 * 发货存储位置no
	 */
	@Schema(description = "交运单明细deliveryStorageLocationNo")
		@NotBlank(message = "交运单明细deliveryStorageLocationNo 不能为空", groups = UpdateGroup.class)
			private String deliveryStorageLocationNo;

	/**
	 * 发货批次号
	 */
	@Schema(description = "交运单明细deliveryBatchNo")
		@NotBlank(message = "交运单明细deliveryBatchNo 不能为空", groups = UpdateGroup.class)
			private String deliveryBatchNo;

	/**
	 * 发货公司主体
	 */
	@Schema(description = "交运单明细deliveryCompanyCode")
		@NotBlank(message = "交运单明细deliveryCompanyCode 不能为空", groups = UpdateGroup.class)
			private String deliveryCompanyCode;

	/**
	 * 创建时间
	 */
	@Schema(description = "交运单明细createTime")
			@NotNull(message = "交运单明细createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "交运单明细updateTime")
			@NotNull(message = "交运单明细updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "交运单明细creatorId")
		@NotBlank(message = "交运单明细creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改人id
	 */
	@Schema(description = "交运单明细updatorId")
		@NotBlank(message = "交运单明细updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "交运单明细deleted")
			@NotNull(message = "交运单明细deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "交运单明细tenantId")
		@NotBlank(message = "交运单明细tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
