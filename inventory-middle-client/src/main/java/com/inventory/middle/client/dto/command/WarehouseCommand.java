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
 * 物理仓库表Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物理仓库表", description = "物理仓库表")
public class WarehouseCommand extends Command {

	/**
	 * 主键ID
	 */
	@Schema(description = "物理仓库表id")
			@NotNull(message = "物理仓库表id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 创建该物理仓库的租户id
	 */
	@Schema(description = "物理仓库表tenantId")
		@NotBlank(message = "物理仓库表tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 物理仓库编码
	 */
	@Schema(description = "物理仓库表warehouseNo")
		@NotBlank(message = "物理仓库表warehouseNo 不能为空", groups = UpdateGroup.class)
			private String warehouseNo;

	/**
	 * 物理仓库名称
	 */
	@Schema(description = "物理仓库表warehouseName")
		@NotBlank(message = "物理仓库表warehouseName 不能为空", groups = UpdateGroup.class)
			private String warehouseName;

	/**
	 * 物理仓类型(1-真实,0-虚拟)
	 */
	@Schema(description = "物理仓库表warehouseType")
			@NotNull(message = "物理仓库表warehouseType 不能为空", groups = UpdateGroup.class)
		private Integer warehouseType;

	/**
	 * 责任人名称
	 */
	@Schema(description = "物理仓库表ownerName")
		@NotBlank(message = "物理仓库表ownerName 不能为空", groups = UpdateGroup.class)
			private String ownerName;

	/**
	 * 仓库电话
	 */
	@Schema(description = "物理仓库表phone")
		@NotBlank(message = "物理仓库表phone 不能为空", groups = UpdateGroup.class)
			private String phone;

	/**
	 * 仓库地址
	 */
	@Schema(description = "物理仓库表address")
		@NotBlank(message = "物理仓库表address 不能为空", groups = UpdateGroup.class)
			private String address;

	/**
	 * 省
	 */
	@Schema(description = "物理仓库表province")
		@NotBlank(message = "物理仓库表province 不能为空", groups = UpdateGroup.class)
			private String province;

	/**
	 * 市
	 */
	@Schema(description = "物理仓库表city")
		@NotBlank(message = "物理仓库表city 不能为空", groups = UpdateGroup.class)
			private String city;

	/**
	 * 区
	 */
	@Schema(description = "物理仓库表region")
		@NotBlank(message = "物理仓库表region 不能为空", groups = UpdateGroup.class)
			private String region;

	/**
	 * 备注
	 */
	@Schema(description = "物理仓库表remark")
		@NotBlank(message = "物理仓库表remark 不能为空", groups = UpdateGroup.class)
			private String remark;

	/**
	 * 创建人
	 */
	@Schema(description = "物理仓库表creatorId")
		@NotBlank(message = "物理仓库表creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 更新人
	 */
	@Schema(description = "物理仓库表updatorId")
		@NotBlank(message = "物理仓库表updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 创建时间
	 */
	@Schema(description = "物理仓库表createTime")
			@NotNull(message = "物理仓库表createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "物理仓库表updateTime")
			@NotNull(message = "物理仓库表updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	@Schema(description = "物理仓库表deleted")
			@NotNull(message = "物理仓库表deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

}
