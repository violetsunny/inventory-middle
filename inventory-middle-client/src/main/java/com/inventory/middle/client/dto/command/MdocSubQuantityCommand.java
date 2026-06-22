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
 * 物料凭证子表-数量Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证子表-数量", description = "物料凭证子表-数量")
public class MdocSubQuantityCommand extends Command {

	/**
	 * 数量信息主键
	 */
	@Schema(description = "物料凭证子表-数量id")
			@NotNull(message = "物料凭证子表-数量id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证id
	 */
	@Schema(description = "物料凭证子表-数量materialDocId")
			@NotNull(message = "物料凭证子表-数量materialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料凭证item
	 */
	@Schema(description = "物料凭证子表-数量materialDocItemId")
			@NotNull(message = "物料凭证子表-数量materialDocItemId 不能为空", groups = UpdateGroup.class)
		private Long materialDocItemId;

	/**
	 * 移动数量
	 */
	@Schema(description = "物料凭证子表-数量adjustQuantity")
			@NotNull(message = "物料凭证子表-数量adjustQuantity 不能为空", groups = UpdateGroup.class)
		private BigDecimal adjustQuantity;

	/**
	 * 计量单位
	 */
	@Schema(description = "物料凭证子表-数量uom")
		@NotBlank(message = "物料凭证子表-数量uom 不能为空", groups = UpdateGroup.class)
			private String uom;

	/**
	 * 不含税单价
	 */
	@Schema(description = "物料凭证子表-数量price")
			@NotNull(message = "物料凭证子表-数量price 不能为空", groups = UpdateGroup.class)
		private BigDecimal price;

	/**
	 * 不含税总价
	 */
	@Schema(description = "物料凭证子表-数量totalPrice")
			@NotNull(message = "物料凭证子表-数量totalPrice 不能为空", groups = UpdateGroup.class)
		private BigDecimal totalPrice;

	/**
	 * 价税总价
	 */
	@Schema(description = "物料凭证子表-数量totalPriceTax")
			@NotNull(message = "物料凭证子表-数量totalPriceTax 不能为空", groups = UpdateGroup.class)
		private BigDecimal totalPriceTax;

	/**
	 * 税码
	 */
	@Schema(description = "物料凭证子表-数量taxCode")
		@NotBlank(message = "物料凭证子表-数量taxCode 不能为空", groups = UpdateGroup.class)
			private String taxCode;

	/**
	 * 税码名称
	 */
	@Schema(description = "物料凭证子表-数量taxName")
		@NotBlank(message = "物料凭证子表-数量taxName 不能为空", groups = UpdateGroup.class)
			private String taxName;

	/**
	 * 税率
	 */
	@Schema(description = "物料凭证子表-数量taxRate")
			@NotNull(message = "物料凭证子表-数量taxRate 不能为空", groups = UpdateGroup.class)
		private BigDecimal taxRate;

	/**
	 * 税额
	 */
	@Schema(description = "物料凭证子表-数量tax")
			@NotNull(message = "物料凭证子表-数量tax 不能为空", groups = UpdateGroup.class)
		private BigDecimal tax;

	/**
	 * 货币
	 */
	@Schema(description = "物料凭证子表-数量currency")
		@NotBlank(message = "物料凭证子表-数量currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 汇率
	 */
	@Schema(description = "物料凭证子表-数量exchangeRate")
			@NotNull(message = "物料凭证子表-数量exchangeRate 不能为空", groups = UpdateGroup.class)
		private BigDecimal exchangeRate;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证子表-数量createTime")
			@NotNull(message = "物料凭证子表-数量createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "物料凭证子表-数量creatorId")
			@NotNull(message = "物料凭证子表-数量creatorId 不能为空", groups = UpdateGroup.class)
		private Long creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证子表-数量updateTime")
			@NotNull(message = "物料凭证子表-数量updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "物料凭证子表-数量updatorId")
			@NotNull(message = "物料凭证子表-数量updatorId 不能为空", groups = UpdateGroup.class)
		private Long updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证子表-数量deleted")
			@NotNull(message = "物料凭证子表-数量deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "物料凭证子表-数量tenantId")
		@NotBlank(message = "物料凭证子表-数量tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
