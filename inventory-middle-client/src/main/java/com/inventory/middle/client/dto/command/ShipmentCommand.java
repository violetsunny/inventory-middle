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
 * 交运单Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "交运单", description = "交运单")
public class ShipmentCommand extends Command {

	/**
	 * 交运单明细id
	 */
	@Schema(description = "交运单id")
			@NotNull(message = "交运单id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 交运单号
	 */
	@Schema(description = "交运单shipmentNo")
		@NotBlank(message = "交运单shipmentNo 不能为空", groups = UpdateGroup.class)
			private String shipmentNo;

	/**
	 * 交运单类型
	 */
	@Schema(description = "交运单shipmentType")
		@NotBlank(message = "交运单shipmentType 不能为空", groups = UpdateGroup.class)
			private String shipmentType;

	/**
	 * 交运单状态
	 */
	@Schema(description = "交运单shipmentStatus")
		@NotBlank(message = "交运单shipmentStatus 不能为空", groups = UpdateGroup.class)
			private String shipmentStatus;

	/**
	 * wms指令类型
	 */
	@Schema(description = "交运单instructionType")
		@NotBlank(message = "交运单instructionType 不能为空", groups = UpdateGroup.class)
			private String instructionType;

	/**
	 * 拣货日期
	 */
	@Schema(description = "交运单pickDate")
			@NotNull(message = "交运单pickDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime pickDate;

	/**
	 * 拣配状态
	 */
	@Schema(description = "交运单pickStatus")
		@NotBlank(message = "交运单pickStatus 不能为空", groups = UpdateGroup.class)
			private String pickStatus;

	/**
	 * 运输线路
	 */
	@Schema(description = "交运单transportRoute")
		@NotBlank(message = "交运单transportRoute 不能为空", groups = UpdateGroup.class)
			private String transportRoute;

	/**
	 * 运输单据号
	 */
	@Schema(description = "交运单transportNo")
		@NotBlank(message = "交运单transportNo 不能为空", groups = UpdateGroup.class)
			private String transportNo;

	/**
	 * 运输主体
	 */
	@Schema(description = "交运单transportCarrier")
		@NotBlank(message = "交运单transportCarrier 不能为空", groups = UpdateGroup.class)
			private String transportCarrier;

	/**
	 * 装运点
	 */
	@Schema(description = "交运单shipmentPoint")
		@NotBlank(message = "交运单shipmentPoint 不能为空", groups = UpdateGroup.class)
			private String shipmentPoint;

	/**
	 * 运输方式
	 */
	@Schema(description = "交运单transportMode")
		@NotBlank(message = "交运单transportMode 不能为空", groups = UpdateGroup.class)
			private String transportMode;

	/**
	 * 发货方名称
	 */
	@Schema(description = "交运单deliveryName")
		@NotBlank(message = "交运单deliveryName 不能为空", groups = UpdateGroup.class)
			private String deliveryName;

	/**
	 * 发货方手机
	 */
	@Schema(description = "交运单deliveryPhone")
		@NotBlank(message = "交运单deliveryPhone 不能为空", groups = UpdateGroup.class)
			private String deliveryPhone;

	/**
	 * 发货地址（省）
	 */
	@Schema(description = "交运单deliveryProvince")
		@NotBlank(message = "交运单deliveryProvince 不能为空", groups = UpdateGroup.class)
			private String deliveryProvince;

	/**
	 * 发货地址（市）
	 */
	@Schema(description = "交运单deliveryCity")
		@NotBlank(message = "交运单deliveryCity 不能为空", groups = UpdateGroup.class)
			private String deliveryCity;

	/**
	 * 发货地址（区）
	 */
	@Schema(description = "交运单deliveryCounty")
		@NotBlank(message = "交运单deliveryCounty 不能为空", groups = UpdateGroup.class)
			private String deliveryCounty;

	/**
	 * 发货地址（详细地址）
	 */
	@Schema(description = "交运单deliveryAddressLine")
		@NotBlank(message = "交运单deliveryAddressLine 不能为空", groups = UpdateGroup.class)
			private String deliveryAddressLine;

	/**
	 * 收货放名称
	 */
	@Schema(description = "交运单receivingName")
		@NotBlank(message = "交运单receivingName 不能为空", groups = UpdateGroup.class)
			private String receivingName;

	/**
	 * 收货方手机
	 */
	@Schema(description = "交运单receivingPhone")
		@NotBlank(message = "交运单receivingPhone 不能为空", groups = UpdateGroup.class)
			private String receivingPhone;

	/**
	 * 收货地址（省）
	 */
	@Schema(description = "交运单receivingProvince")
		@NotBlank(message = "交运单receivingProvince 不能为空", groups = UpdateGroup.class)
			private String receivingProvince;

	/**
	 * 收货地址（市）
	 */
	@Schema(description = "交运单receivingCity")
		@NotBlank(message = "交运单receivingCity 不能为空", groups = UpdateGroup.class)
			private String receivingCity;

	/**
	 * 收货地址（区）
	 */
	@Schema(description = "交运单receivingCounty")
		@NotBlank(message = "交运单receivingCounty 不能为空", groups = UpdateGroup.class)
			private String receivingCounty;

	/**
	 * 收货地址（详细地址）
	 */
	@Schema(description = "交运单receivingAddressLine")
		@NotBlank(message = "交运单receivingAddressLine 不能为空", groups = UpdateGroup.class)
			private String receivingAddressLine;

	/**
	 * 计划发货日期
	 */
	@Schema(description = "交运单plannedDeliveryDate")
			@NotNull(message = "交运单plannedDeliveryDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime plannedDeliveryDate;

	/**
	 * 计划收货日期
	 */
	@Schema(description = "交运单plannedReceivingDate")
			@NotNull(message = "交运单plannedReceivingDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime plannedReceivingDate;

	/**
	 * 实际发货日期
	 */
	@Schema(description = "交运单actualDeliveryDate")
			@NotNull(message = "交运单actualDeliveryDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime actualDeliveryDate;

	/**
	 * 实际收货日期
	 */
	@Schema(description = "交运单actualReceivingDate")
			@NotNull(message = "交运单actualReceivingDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime actualReceivingDate;

	/**
	 * 创建时间
	 */
	@Schema(description = "交运单createTime")
			@NotNull(message = "交运单createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "交运单creatorId")
		@NotBlank(message = "交运单creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "交运单updateTime")
			@NotNull(message = "交运单updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "交运单updatorId")
		@NotBlank(message = "交运单updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "交运单deleted")
			@NotNull(message = "交运单deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "交运单tenantId")
		@NotBlank(message = "交运单tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
