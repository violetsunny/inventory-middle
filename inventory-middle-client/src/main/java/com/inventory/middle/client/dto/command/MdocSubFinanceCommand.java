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
 * 物料凭证-标签-财务Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "物料凭证-标签-财务", description = "物料凭证-标签-财务")
public class MdocSubFinanceCommand extends Command {

	/**
	 * id
	 */
	@Schema(description = "物料凭证-标签-财务id")
			@NotNull(message = "物料凭证-标签-财务id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * 物料凭证id
	 */
	@Schema(description = "物料凭证-标签-财务materialDocId")
			@NotNull(message = "物料凭证-标签-财务materialDocId 不能为空", groups = UpdateGroup.class)
		private Long materialDocId;

	/**
	 * 物料凭证itemId
	 */
	@Schema(description = "物料凭证-标签-财务materialDocItemId")
			@NotNull(message = "物料凭证-标签-财务materialDocItemId 不能为空", groups = UpdateGroup.class)
		private Long materialDocItemId;

	/**
	 * 资产号
	 */
	@Schema(description = "物料凭证-标签-财务assertTag")
		@NotBlank(message = "物料凭证-标签-财务assertTag 不能为空", groups = UpdateGroup.class)
			private String assertTag;

	/**
	 * 次级编号
	 */
	@Schema(description = "物料凭证-标签-财务subAssertTag")
		@NotBlank(message = "物料凭证-标签-财务subAssertTag 不能为空", groups = UpdateGroup.class)
			private String subAssertTag;

	/**
	 * 利润中心名称
	 */
	@Schema(description = "物料凭证-标签-财务profitCenterName")
		@NotBlank(message = "物料凭证-标签-财务profitCenterName 不能为空", groups = UpdateGroup.class)
			private String profitCenterName;

	/**
	 * 利润中心
	 */
	@Schema(description = "物料凭证-标签-财务profitCenterCode")
		@NotBlank(message = "物料凭证-标签-财务profitCenterCode 不能为空", groups = UpdateGroup.class)
			private String profitCenterCode;

	/**
	 * 成本中心名称
	 */
	@Schema(description = "物料凭证-标签-财务costCenterName")
		@NotBlank(message = "物料凭证-标签-财务costCenterName 不能为空", groups = UpdateGroup.class)
			private String costCenterName;

	/**
	 * 成本中心
	 */
	@Schema(description = "物料凭证-标签-财务costCenterCode")
		@NotBlank(message = "物料凭证-标签-财务costCenterCode 不能为空", groups = UpdateGroup.class)
			private String costCenterCode;

	/**
	 * 产品线
	 */
	@Schema(description = "物料凭证-标签-财务productLine")
		@NotBlank(message = "物料凭证-标签-财务productLine 不能为空", groups = UpdateGroup.class)
			private String productLine;

	/**
	 * 贸易伙伴
	 */
	@Schema(description = "物料凭证-标签-财务tradePartner")
		@NotBlank(message = "物料凭证-标签-财务tradePartner 不能为空", groups = UpdateGroup.class)
			private String tradePartner;

	/**
	 * 供应商名称
	 */
	@Schema(description = "物料凭证-标签-财务supplyName")
		@NotBlank(message = "物料凭证-标签-财务supplyName 不能为空", groups = UpdateGroup.class)
			private String supplyName;

	/**
	 * 供应商编码
	 */
	@Schema(description = "物料凭证-标签-财务supplyCode")
		@NotBlank(message = "物料凭证-标签-财务supplyCode 不能为空", groups = UpdateGroup.class)
			private String supplyCode;

	/**
	 * 客户名称
	 */
	@Schema(description = "物料凭证-标签-财务customerName")
		@NotBlank(message = "物料凭证-标签-财务customerName 不能为空", groups = UpdateGroup.class)
			private String customerName;

	/**
	 * 客户编码
	 */
	@Schema(description = "物料凭证-标签-财务customerCode")
		@NotBlank(message = "物料凭证-标签-财务customerCode 不能为空", groups = UpdateGroup.class)
			private String customerCode;

	/**
	 * 结算方式
	 */
	@Schema(description = "物料凭证-标签-财务settlementType")
			@NotNull(message = "物料凭证-标签-财务settlementType 不能为空", groups = UpdateGroup.class)
		private Integer settlementType;

	/**
	 * 营销活动编码
	 */
	@Schema(description = "物料凭证-标签-财务marketingNo")
		@NotBlank(message = "物料凭证-标签-财务marketingNo 不能为空", groups = UpdateGroup.class)
			private String marketingNo;

	/**
	 * 预算编码
	 */
	@Schema(description = "物料凭证-标签-财务budgetNo")
		@NotBlank(message = "物料凭证-标签-财务budgetNo 不能为空", groups = UpdateGroup.class)
			private String budgetNo;

	/**
	 * 内部订单号
	 */
	@Schema(description = "物料凭证-标签-财务internalOrderNo")
		@NotBlank(message = "物料凭证-标签-财务internalOrderNo 不能为空", groups = UpdateGroup.class)
			private String internalOrderNo;

	/**
	 * 备注
	 */
	@Schema(description = "物料凭证-标签-财务remark")
		@NotBlank(message = "物料凭证-标签-财务remark 不能为空", groups = UpdateGroup.class)
			private String remark;

	/**
	 * 删除标识
	 */
	@Schema(description = "物料凭证-标签-财务deleted")
			@NotNull(message = "物料凭证-标签-财务deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户id
	 */
	@Schema(description = "物料凭证-标签-财务tenantId")
		@NotBlank(message = "物料凭证-标签-财务tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

	/**
	 * 创建时间
	 */
	@Schema(description = "物料凭证-标签-财务createTime")
			@NotNull(message = "物料凭证-标签-财务createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人id
	 */
	@Schema(description = "物料凭证-标签-财务creatorId")
			@NotNull(message = "物料凭证-标签-财务creatorId 不能为空", groups = UpdateGroup.class)
		private Long creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "物料凭证-标签-财务updateTime")
			@NotNull(message = "物料凭证-标签-财务updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人id
	 */
	@Schema(description = "物料凭证-标签-财务updatorId")
			@NotNull(message = "物料凭证-标签-财务updatorId 不能为空", groups = UpdateGroup.class)
		private Long updatorId;

}
