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
 * 物料凭证子表-出入库信息Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证子表-出入库信息", description = "物料凭证子表-出入库信息")
public class MdocSubInOutCommand extends Command {

	/**
	 * 出入库信息主键
	 */
	@Schema(description = "物料凭证子表-出入库信息id")
			@NotNull(message = "物料凭证子表-出入库信息id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证id
	 */
	@Schema(description = "物料凭证子表-出入库信息materialDocId")
			@NotNull(message = "物料凭证子表-出入库信息materialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料凭证item
	 */
	@Schema(description = "物料凭证子表-出入库信息materialDocItemId")
			@NotNull(message = "物料凭证子表-出入库信息materialDocItemId 不能为空", groups = UpdateGroup.class)
		private Long materialDocItemId;

	/**
	 * 发货方名称
	 */
	@Schema(description = "物料凭证子表-出入库信息demand")
		@NotBlank(message = "物料凭证子表-出入库信息demand 不能为空", groups = UpdateGroup.class)
			private String demand;

	/**
	 * 发货销售订单号
	 */
	@Schema(description = "物料凭证子表-出入库信息demandSaleOrderNo")
		@NotBlank(message = "物料凭证子表-出入库信息demandSaleOrderNo 不能为空", groups = UpdateGroup.class)
			private String demandSaleOrderNo;

	/**
	 * 发货销售订单行号
	 */
	@Schema(description = "物料凭证子表-出入库信息demandSaleOrderItemNo")
		@NotBlank(message = "物料凭证子表-出入库信息demandSaleOrderItemNo 不能为空", groups = UpdateGroup.class)
			private String demandSaleOrderItemNo;

	/**
	 * 发货方库存地点no
	 */
	@Schema(description = "物料凭证子表-出入库信息demandStorageLocationNo")
		@NotBlank(message = "物料凭证子表-出入库信息demandStorageLocationNo 不能为空", groups = UpdateGroup.class)
			private String demandStorageLocationNo;

	/**
	 * 发货方库存地点名称
	 */
	@Schema(description = "物料凭证子表-出入库信息demandStorageLocationName")
		@NotBlank(message = "物料凭证子表-出入库信息demandStorageLocationName 不能为空", groups = UpdateGroup.class)
			private String demandStorageLocationName;

	/**
	 * 发货方批次号
	 */
	@Schema(description = "物料凭证子表-出入库信息demandBatchNo")
		@NotBlank(message = "物料凭证子表-出入库信息demandBatchNo 不能为空", groups = UpdateGroup.class)
			private String demandBatchNo;

	/**
	 * 发货方库存类型(良品、残次品、质检品)
	 */
	@Schema(description = "物料凭证子表-出入库信息demandStockType")
			@NotNull(message = "物料凭证子表-出入库信息demandStockType 不能为空", groups = UpdateGroup.class)
		private Integer demandStockType;

	/**
	 * 收货方名称
	 */
	@Schema(description = "物料凭证子表-出入库信息supply")
		@NotBlank(message = "物料凭证子表-出入库信息supply 不能为空", groups = UpdateGroup.class)
			private String supply;

	/**
	 * 收货销售订单号
	 */
	@Schema(description = "物料凭证子表-出入库信息supplySaleOrderNo")
		@NotBlank(message = "物料凭证子表-出入库信息supplySaleOrderNo 不能为空", groups = UpdateGroup.class)
			private String supplySaleOrderNo;

	/**
	 * 收货销售订单行号
	 */
	@Schema(description = "物料凭证子表-出入库信息supplySaleOrderItemNo")
		@NotBlank(message = "物料凭证子表-出入库信息supplySaleOrderItemNo 不能为空", groups = UpdateGroup.class)
			private String supplySaleOrderItemNo;

	/**
	 * 收货方库存地点no
	 */
	@Schema(description = "物料凭证子表-出入库信息supplyStorageLocationNo")
		@NotBlank(message = "物料凭证子表-出入库信息supplyStorageLocationNo 不能为空", groups = UpdateGroup.class)
			private String supplyStorageLocationNo;

	/**
	 * 收货方库存地点名称
	 */
	@Schema(description = "物料凭证子表-出入库信息supplyStorageLocationName")
		@NotBlank(message = "物料凭证子表-出入库信息supplyStorageLocationName 不能为空", groups = UpdateGroup.class)
			private String supplyStorageLocationName;

	/**
	 * 收货方批次号
	 */
	@Schema(description = "物料凭证子表-出入库信息supplyBatchNo")
		@NotBlank(message = "物料凭证子表-出入库信息supplyBatchNo 不能为空", groups = UpdateGroup.class)
			private String supplyBatchNo;

	/**
	 * 收货方库存类型(良品、残次品、质检品)
	 */
	@Schema(description = "物料凭证子表-出入库信息supplyStockType")
			@NotNull(message = "物料凭证子表-出入库信息supplyStockType 不能为空", groups = UpdateGroup.class)
		private Integer supplyStockType;

	/**
	 * 移动类型
	 */
	@Schema(description = "物料凭证子表-出入库信息adjustType")
		@NotBlank(message = "物料凭证子表-出入库信息adjustType 不能为空", groups = UpdateGroup.class)
			private String adjustType;

	/**
	 * 移动原因
	 */
	@Schema(description = "物料凭证子表-出入库信息adjustReason")
		@NotBlank(message = "物料凭证子表-出入库信息adjustReason 不能为空", groups = UpdateGroup.class)
			private String adjustReason;

	/**
	 * 出入库标识
	 */
	@Schema(description = "物料凭证子表-出入库信息io")
		@NotBlank(message = "物料凭证子表-出入库信息io 不能为空", groups = UpdateGroup.class)
			private String io;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证子表-出入库信息createTime")
			@NotNull(message = "物料凭证子表-出入库信息createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "物料凭证子表-出入库信息creatorId")
		@NotBlank(message = "物料凭证子表-出入库信息creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证子表-出入库信息updateTime")
			@NotNull(message = "物料凭证子表-出入库信息updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "物料凭证子表-出入库信息updatorId")
		@NotBlank(message = "物料凭证子表-出入库信息updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证子表-出入库信息deleted")
			@NotNull(message = "物料凭证子表-出入库信息deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "物料凭证子表-出入库信息tenantId")
		@NotBlank(message = "物料凭证子表-出入库信息tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
