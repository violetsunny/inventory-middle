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
 * 物料凭证主表Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证主表", description = "物料凭证主表")
public class MaterialDocMainCommand extends Command {

	/**
	 * 物料凭证Id
	 */
	@Schema(description = "物料凭证主表id")
			@NotNull(message = "物料凭证主表id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证编号
	 */
	@Schema(description = "物料凭证主表materialDocNo")
		@NotBlank(message = "物料凭证主表materialDocNo 不能为空", groups = UpdateGroup.class)
			private String materialDocNo;

	/**
	 * 凭证类型
	 */
	@Schema(description = "物料凭证主表docCategory")
			@NotNull(message = "物料凭证主表docCategory 不能为空", groups = UpdateGroup.class)
		private Integer docCategory;

	/**
	 * 凭证编码组
	 */
	@Schema(description = "物料凭证主表docGroupNo")
		@NotBlank(message = "物料凭证主表docGroupNo 不能为空", groups = UpdateGroup.class)
			private String docGroupNo;

	/**
	 * 凭证类别
	 */
	@Schema(description = "物料凭证主表docType")
			@NotNull(message = "物料凭证主表docType 不能为空", groups = UpdateGroup.class)
		private Integer docType;

	/**
	 * 创建日期
	 */
	@Schema(description = "物料凭证主表publishDate")
			@NotNull(message = "物料凭证主表publishDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime publishDate;

	/**
	 * 过账日期
	 */
	@Schema(description = "物料凭证主表postingDate")
			@NotNull(message = "物料凭证主表postingDate 不能为空", groups = UpdateGroup.class)
		private LocalDateTime postingDate;

	/**
	 * 参照业务单据号
	 */
	@Schema(description = "物料凭证主表originalNo")
		@NotBlank(message = "物料凭证主表originalNo 不能为空", groups = UpdateGroup.class)
			private String originalNo;

	/**
	 * 业务单据类型
	 */
	@Schema(description = "物料凭证主表originalNoType")
			@NotNull(message = "物料凭证主表originalNoType 不能为空", groups = UpdateGroup.class)
		private Integer originalNoType;

	/**
	 * 交运单号
	 */
	@Schema(description = "物料凭证主表deliverNo")
		@NotBlank(message = "物料凭证主表deliverNo 不能为空", groups = UpdateGroup.class)
			private String deliverNo;

	/**
	 * 库存所有者
	 */
	@Schema(description = "物料凭证主表owner")
		@NotBlank(message = "物料凭证主表owner 不能为空", groups = UpdateGroup.class)
			private String owner;

	/**
	 * 移动类型
	 */
	@Schema(description = "物料凭证主表adjustType")
		@NotBlank(message = "物料凭证主表adjustType 不能为空", groups = UpdateGroup.class)
			private String adjustType;

	/**
	 * 收货方逻辑仓库no
	 */
	@Schema(description = "物料凭证主表supplyLogicalPlantNo")
		@NotBlank(message = "物料凭证主表supplyLogicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String supplyLogicalPlantNo;

	/**
	 * 发货方逻辑仓库no
	 */
	@Schema(description = "物料凭证主表demandLogicalPlantNo")
		@NotBlank(message = "物料凭证主表demandLogicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String demandLogicalPlantNo;

	/**
	 * map流水号
	 */
	@Schema(description = "物料凭证主表mapCode")
		@NotBlank(message = "物料凭证主表mapCode 不能为空", groups = UpdateGroup.class)
			private String mapCode;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证主表createTime")
			@NotNull(message = "物料凭证主表createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人ID
	 */
	@Schema(description = "物料凭证主表creatorId")
		@NotBlank(message = "物料凭证主表creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证主表updateTime")
			@NotNull(message = "物料凭证主表updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人ID
	 */
	@Schema(description = "物料凭证主表updatorId")
		@NotBlank(message = "物料凭证主表updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证主表deleted")
			@NotNull(message = "物料凭证主表deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户ID
	 */
	@Schema(description = "物料凭证主表tenantId")
		@NotBlank(message = "物料凭证主表tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 备注
	 */
	@Schema(description = "物料凭证主表remark")
		@NotBlank(message = "物料凭证主表remark 不能为空", groups = UpdateGroup.class)
			private String remark;

	/**
	 * 外部业务唯一号
	 */
	@Schema(description = "物料凭证主表uniqueNo")
		@NotBlank(message = "物料凭证主表uniqueNo 不能为空", groups = UpdateGroup.class)
			private String uniqueNo;

	/**
	 * 操作应用服务标识
	 */
	@Schema(description = "物料凭证主表appKey")
		@NotBlank(message = "物料凭证主表appKey 不能为空", groups = UpdateGroup.class)
			private String appKey;

}
