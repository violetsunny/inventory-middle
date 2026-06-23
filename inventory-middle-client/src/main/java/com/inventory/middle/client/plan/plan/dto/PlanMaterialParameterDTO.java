package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.BaseDTO;
import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询物料计划参数 返参
 * @date 2021/9/30 16:03
 */
@Data
public class PlanMaterialParameterDTO extends BaseDTO implements ExternalMaterialSupport, Serializable {

    private static final long serialVersionUID = 5985374808265560922L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 外部物料编码
     */
    private String externalMaterialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 计划类型（0：采购，1：调拨，2：生产）
     */
    private Integer planTypeCode;

    /**
     * 计划类型（0：采购，1：调拨，2：生产）
     */
    private String planTypeDesc;

    /**
     * 物料需求类型
     */
    private Integer demandType;

    /**
     * 需求策略编码
     */
    private String demandStrategyCode;

    /**
     * 物料提前期
     */
    private Integer vendorLeadTime;

    /**
     * 计划时区
     */
    private Integer planningTimeFence;

    /**
     * 需求时区
     */
    private Integer demandTimeFence;

    /**
     * 订货批量
     */
    private Long orderQuantity;

    /**
     * 最小订货批量
     */
    private Long minOrderQuantity;

    /**
     * 订货周期
     */
    private Integer orderCycleTime;

    /**
     * 安全库存计算方式
     */
    private Integer safetyStockCalType;

    /**
     * 安全库存
     */
    private Long safetyStock;

    /**
     * 安全库存公式计算参数<br/>
     * 安全系数:safetyCoefficient
     * 保障间隔:guaranteeInterval
     * 保障间隔内的需求标准差:demandStd
     */
    private Map<String, Object> safetyStockFactors;

    /**
     * 损耗率
     */
    private Integer lossRate;

    /**
     * 成品率
     */
    private Integer finishedRate;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建时间字符串格式
     */
    private String createTimeStr;

    /**
     * 表格索引
     */
    private Integer index;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 调拨逻辑仓编码
     */
    private String transferLogicalPlantNo;


}
