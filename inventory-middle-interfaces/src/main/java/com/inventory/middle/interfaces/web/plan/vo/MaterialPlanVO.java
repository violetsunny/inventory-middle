package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物料计划
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划记录")
public class MaterialPlanVO implements Serializable {

    private static final long serialVersionUID = -7846937750002791385L;

    /**
     * 主键
     */
    @Schema(description = "物料计划版本号")
    private Long id;

    /**
     * 实例id
     */
    @Schema(description = "计划版本号")
    private Long instanceId;

    /**
     * 计划类型
     */
    @Schema(description = "计划类型")
    private Integer planType;

    /**
     * 计划方案号
     */
    @Schema(description = "计划方案号")
    private String planCode;

    /**
     * 计划方案号
     */
    @Schema(description = "计划版本号")
    private String planVersion;

    /**
     * 物料编码
     */
    @Schema(description = "物料编码")
    private String materialCode;

    /**
     * 物料描述
     */
    @Schema(description = "物料描述")
    private String materialDesc;

    /**
     * 外部物料编码
     */
    @Schema(description = "外部物料编码")
    private String externalMaterialCode;

    /**
     * 逻辑仓编码
     */
    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    @Schema(description = "逻辑仓名称")
    private String logicalPlantName;

    /**
     * 需求响应策略编码
     */
    @Schema(description = "需求响应策略编码")
    private String demandStrategyCode;

    /**
     * 物料计划实例状态
     */
    @Schema(description = "计划状态")
    private Integer status;

    /**
     * 创建时间（默认当前时间）
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createUserId;

    /**
     * 租户id
     */
    @Schema(description = "租户")
    private String tenantId;
}
