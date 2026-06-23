package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author caosheng
 * @title: PageQueryPlanOrderDTO
 * @projectName scm-plan-bff
 * @description: 分页查询计划订单信息
 * @date 2021/10/26 15:13
 */
@Data
public class PageQueryPlanOrderDTO implements Serializable {

    private static final long serialVersionUID = 4730933610999331796L;

    /**
     * 计划订单号
     */
    @Schema(description = "计划订单号")
    private String orderNo;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date maxCreateTime;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date minCreateTime;

    /**
     * 计划订单状态(0：已创建，1：已确认，2：部分下发，3：全部下发，4：已完结，5：已逾期)
     */
    @Schema(description = "计划订单状态(0：已创建，1：已确认，2：部分下发，3：全部下发，4：已完结，5：已逾期)")
    private Integer status;

    /**
     * 物料编码
     */
    @Schema(description = "物料编码")
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    /**
     * 租户id
     */
    @Schema(description = "租户id", required = true)
    private String tenantId;

    /**
     * 页码
     */
    @Schema(description = "页码:从1起始", required = true)
    private Integer pageNum;

    /**
     * 页大小
     */
    @Schema(description = "分页大小", required = true)
    private Integer pageSize;
}
