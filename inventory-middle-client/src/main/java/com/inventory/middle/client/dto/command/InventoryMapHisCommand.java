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
 * 移动平均价历史记录Command
 * 
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Schema(name = "移动平均价历史记录", description = "移动平均价历史记录")
public class InventoryMapHisCommand extends Command {

	/**
	 * MAP历史记录主键
	 */
	@Schema(description = "移动平均价历史记录id")
			@NotNull(message = "移动平均价历史记录id 不能为空", groups = UpdateGroup.class)
		private Long id;

	/**
	 * map主键
	 */
	@Schema(description = "移动平均价历史记录mapId")
			@NotNull(message = "移动平均价历史记录mapId 不能为空", groups = UpdateGroup.class)
		private Long mapId;

	/**
	 * map流水号
	 */
	@Schema(description = "移动平均价历史记录mapCode")
		@NotBlank(message = "移动平均价历史记录mapCode 不能为空", groups = UpdateGroup.class)
			private String mapCode;

	/**
	 * map子流水code
	 */
	@Schema(description = "移动平均价历史记录mapSubCode")
		@NotBlank(message = "移动平均价历史记录mapSubCode 不能为空", groups = UpdateGroup.class)
			private String mapSubCode;

	/**
	 * 物料code
	 */
	@Schema(description = "移动平均价历史记录skuCode")
		@NotBlank(message = "移动平均价历史记录skuCode 不能为空", groups = UpdateGroup.class)
			private String skuCode;

	/**
	 * 逻辑仓
	 */
	@Schema(description = "移动平均价历史记录logicalPlantNo")
		@NotBlank(message = "移动平均价历史记录logicalPlantNo 不能为空", groups = UpdateGroup.class)
			private String logicalPlantNo;

	/**
	 * 价格总和
	 */
	@Schema(description = "移动平均价历史记录skuPriceTotal")
			@NotNull(message = "移动平均价历史记录skuPriceTotal 不能为空", groups = UpdateGroup.class)
		private BigDecimal skuPriceTotal;

	/**
	 * 数量总和
	 */
	@Schema(description = "移动平均价历史记录skuStockTotal")
			@NotNull(message = "移动平均价历史记录skuStockTotal 不能为空", groups = UpdateGroup.class)
		private BigDecimal skuStockTotal;

	/**
	 * 移动平均价
	 */
	@Schema(description = "移动平均价历史记录map")
			@NotNull(message = "移动平均价历史记录map 不能为空", groups = UpdateGroup.class)
		private BigDecimal map;

	/**
	 * 货币单位
	 */
	@Schema(description = "移动平均价历史记录currency")
		@NotBlank(message = "移动平均价历史记录currency 不能为空", groups = UpdateGroup.class)
			private String currency;

	/**
	 * 汇率
	 */
	@Schema(description = "移动平均价历史记录exchangeRate")
			@NotNull(message = "移动平均价历史记录exchangeRate 不能为空", groups = UpdateGroup.class)
		private BigDecimal exchangeRate;

	/**
	 * 创建时间
	 */
	@Schema(description = "移动平均价历史记录createTime")
			@NotNull(message = "移动平均价历史记录createTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime createTime;

	/**
	 * 创建人ID
	 */
	@Schema(description = "移动平均价历史记录creatorId")
		@NotBlank(message = "移动平均价历史记录creatorId 不能为空", groups = UpdateGroup.class)
			private String creatorId;

	/**
	 * 修改时间
	 */
	@Schema(description = "移动平均价历史记录updateTime")
			@NotNull(message = "移动平均价历史记录updateTime 不能为空", groups = UpdateGroup.class)
		private LocalDateTime updateTime;

	/**
	 * 修改人ID
	 */
	@Schema(description = "移动平均价历史记录updatorId")
		@NotBlank(message = "移动平均价历史记录updatorId 不能为空", groups = UpdateGroup.class)
			private String updatorId;

	/**
	 * 删除标识
	 */
	@Schema(description = "移动平均价历史记录deleted")
			@NotNull(message = "移动平均价历史记录deleted 不能为空", groups = UpdateGroup.class)
		private Integer deleted;

	/**
	 * 租户ID
	 */
	@Schema(description = "移动平均价历史记录tenantId")
		@NotBlank(message = "移动平均价历史记录tenantId 不能为空", groups = UpdateGroup.class)
			private String tenantId;

}
