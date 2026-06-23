package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 创建计划 入参
 * @date 2021/10/1 19:34
 */
@Data
public class PlanCreateReqDTO implements Serializable {

    private static final long serialVersionUID = -7468717209144162963L;

    @Schema(description = "方案描述" , required = true)
    private String planDesc;

    @Schema(description = "覆盖逻辑仓（逻辑仓编码集合）" , required = true)
    private List<String> coverLogicalPlantNos;

    @Schema(description = "覆盖物料类型 0-所有物料 1-指定物料" , required = true)
    private Integer coverMaterialType;

    @Schema(description = "计划起始时间 格式：yyyy-MM-dd" , required = true)
    private String planStartTimeStr;

    @Schema(description = "计划执行类型" , required = true)
    private Integer planType;

    @Schema(description = "计划展望期(天)" , required = true)
    private Integer planHorizon;

    @Schema(description = "需求文件（需求计划ID集合）" , required = true)
    private List<String> demandPlanIds;

    @Schema(description = "计算参数（k:v 规则联系开发人员）", required = true,
            example = "{'orderCalRule':'订货计算规则','orderCalInterval':'计算间隔','orderProduceType':'订货量类型','enableLossRate':'是否启用损耗率','enableFinishedRate':'是否启用成品率','enableSafetyStock':'是否启动安全库存'}")
    private Map<String, Object> planCalculateParams;

    @Schema(description = "投放参数（k:v 规则联系开发人员）", required = true,
            example = "{'purchaserId':'采购默认申请人ID','purchaserName':'采购默认申请人姓名','transferId':'调拨默认申请人ID','transferName':'调拨默认申请人姓名'}")
    private Map<String, Object> planDeliveryParams;

    @Schema(description = "是否考虑BOM")
    private Integer relatedBom;

}