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
 * 物料凭证子表-物料信息Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证子表-物料信息", description = "物料凭证子表-物料信息")
public class MdocSubMaterialCommand extends Command {

	/**
	 * 物料信息主键
	 */
	@Schema(description = "物料凭证子表-物料信息id")
			@NotNull(message = "物料凭证子表-物料信息id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证ID
	 */
	@Schema(description = "物料凭证子表-物料信息materialDocId")
			@NotNull(message = "物料凭证子表-物料信息materialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料凭证item
	 */
	@Schema(description = "物料凭证子表-物料信息materialDocItemId")
			@NotNull(message = "物料凭证子表-物料信息materialDocItemId 不能为空", groups = UpdateGroup.class)
		private Long materialDocItemId;

	/**
	 * 物料code
	 */
	@Schema(description = "物料凭证子表-物料信息materialCode")
		@NotBlank(message = "物料凭证子表-物料信息materialCode 不能为空", groups = UpdateGroup.class)
			private String materialCode;

	/**
	 * 物料名称
	 */
	@Schema(description = "物料凭证子表-物料信息materialName")
		@NotBlank(message = "物料凭证子表-物料信息materialName 不能为空", groups = UpdateGroup.class)
			private String materialName;

	/**
	 * 物料品类
	 */
	@Schema(description = "物料凭证子表-物料信息materialCategoryCode")
		@NotBlank(message = "物料凭证子表-物料信息materialCategoryCode 不能为空", groups = UpdateGroup.class)
			private String materialCategoryCode;

	/**
	 * 物料重量
	 */
	@Schema(description = "物料凭证子表-物料信息materialWeight")
			@NotNull(message = "物料凭证子表-物料信息materialWeight 不能为空", groups = UpdateGroup.class)
		private BigDecimal materialWeight;

	/**
	 * 物料重量单位
	 */
	@Schema(description = "物料凭证子表-物料信息weightUnit")
		@NotBlank(message = "物料凭证子表-物料信息weightUnit 不能为空", groups = UpdateGroup.class)
			private String weightUnit;

	/**
	 * 物料体积
	 */
	@Schema(description = "物料凭证子表-物料信息materialVolume")
			@NotNull(message = "物料凭证子表-物料信息materialVolume 不能为空", groups = UpdateGroup.class)
		private BigDecimal materialVolume;

	/**
	 * 物料体积单位
	 */
	@Schema(description = "物料凭证子表-物料信息volumeUnit")
		@NotBlank(message = "物料凭证子表-物料信息volumeUnit 不能为空", groups = UpdateGroup.class)
			private String volumeUnit;

	/**
	 * 评估类
	 */
	@Schema(description = "物料凭证子表-物料信息valuation")
		@NotBlank(message = "物料凭证子表-物料信息valuation 不能为空", groups = UpdateGroup.class)
			private String valuation;

	/**
	 * 备注1
	 */
	@Schema(description = "物料凭证子表-物料信息remark1")
		@NotBlank(message = "物料凭证子表-物料信息remark1 不能为空", groups = UpdateGroup.class)
			private String remark1;

	/**
	 * 备注2
	 */
	@Schema(description = "物料凭证子表-物料信息remark2")
		@NotBlank(message = "物料凭证子表-物料信息remark2 不能为空", groups = UpdateGroup.class)
			private String remark2;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证子表-物料信息createTime")
			@NotNull(message = "物料凭证子表-物料信息createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人ID
	 */
	@Schema(description = "物料凭证子表-物料信息creatorId")
			@NotNull(message = "物料凭证子表-物料信息creatorId 不能为空", groups = UpdateGroup.class)
		private Long creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证子表-物料信息updateTime")
			@NotNull(message = "物料凭证子表-物料信息updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人ID
	 */
	@Schema(description = "物料凭证子表-物料信息updatorId")
			@NotNull(message = "物料凭证子表-物料信息updatorId 不能为空", groups = UpdateGroup.class)
		private Long updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证子表-物料信息deleted")
			@NotNull(message = "物料凭证子表-物料信息deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户ID
	 */
	@Schema(description = "物料凭证子表-物料信息tenantId")
		@NotBlank(message = "物料凭证子表-物料信息tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
