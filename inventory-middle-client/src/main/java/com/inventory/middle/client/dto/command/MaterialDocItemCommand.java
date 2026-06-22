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
 * 物料凭证-itemCommand
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证-item", description = "物料凭证-item")
public class MaterialDocItemCommand extends Command {

	/**
	 * 物料凭证itemId
	 */
	@Schema(description = "物料凭证-itemid")
			@NotNull(message = "物料凭证-itemid 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证id
	 */
	@Schema(description = "物料凭证-itemmaterialDocId")
			@NotNull(message = "物料凭证-itemmaterialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料code
	 */
	@Schema(description = "物料凭证-itemmaterialCode")
		@NotBlank(message = "物料凭证-itemmaterialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 批次号
	 */
	@Schema(description = "物料凭证-itembatchNo")
		@NotBlank(message = "物料凭证-itembatchNo 不能为空", groups = UpdateGroup.class)
			private String batchNo;

	/**
	 * 出入库类型1-入库,2-出库
	 */
	@Schema(description = "物料凭证-itemitemCategory")
			@NotNull(message = "物料凭证-itemitemCategory 不能为空", groups = UpdateGroup.class)
		private Integer itemCategory;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证-itemcreateTime")
			@NotNull(message = "物料凭证-itemcreateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "物料凭证-itemcreatorId")
		@NotBlank(message = "物料凭证-itemcreatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证-itemupdateTime")
			@NotNull(message = "物料凭证-itemupdateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "物料凭证-itemupdatorId")
		@NotBlank(message = "物料凭证-itemupdatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证-itemdeleted")
			@NotNull(message = "物料凭证-itemdeleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "物料凭证-itemtenantId")
		@NotBlank(message = "物料凭证-itemtenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
