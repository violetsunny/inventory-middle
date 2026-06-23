package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 17:10
 */
@Data
@Schema(description = "供需存看板详情数据")
public class DemandSupplyStockBoardDetailDataVO implements Serializable {

    private static final long serialVersionUID = -6858494408138155600L;

    @Schema(description = "时间")
    private Date planDate;

    @Schema(description = "计划元素")
    private Integer planElementType;

    @Schema(description = "单据")
    private String voucherNo;

    @Schema(description = "行号")
    private String documentNo;

    @Schema(description = "异常码")
    private String exceptionCode;

    @Schema(description = "需求量")
    private Long demandAmount;

    @Schema(description = "供给量")
    private Long supplyAmount;

    @Schema(description = "可用数量")
    private Long availableAmount;
}
