package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;

import com.inventory.middle.infra.persistence.entity.BasePO;

import java.util.Date;

/**
 * pl_plan
 *
 * @date 2021-10-01
 */
@Data
public class PlanPO extends BasePO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 方案编码
     */
    private String planCode;

    /**
     * 方案描述
     */
    private String planDesc;

    /**
     * 方案类型
     */
    private Integer planType;

    /**
     * 覆盖逻辑仓（JSON数组）
     */
    private String coverLogicalPlant;

    /**
     * 覆盖物料类型（0-全部物料/1-指定物料）
     */
    private Integer coverMaterialType;

    /**
     * 计划展望期(天)
     */
    private Integer planHorizon;

    /**
     * 计划开始时间
     */
    private Date planStartTime;

    /**
     * 需求计划文件（JSON结构）
     */
    private String demandPlanFile;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     *//**
     * 创建时间（默认当前时间）
     *//**
     * 创建人
     *//**
     * 更新时间（默认当前时间）
     *//**
     * 更新人
     *//**
     * 租户id
     */
    private String tenantId;

    /**
     * 计算参数（JSON结构）
     */
    private String planCalculateParams;

    /**
     * 投放参数（JSON结构）
     */
    private String planDeliveryParams;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 是否关联bom(0：不关联，1关联，默认0)
     */
    private Integer relatedBom;

    public Integer getRelatedBom() {
        return relatedBom;
    }

    public void setRelatedBom(Integer relatedBom) {
        this.relatedBom = relatedBom;
    }

    /**
     * 主键
     *
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 方案编码
     *
     * @return plan_code 方案编码
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * 方案编码
     *
     * @param planCode 方案编码
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    /**
     * 方案描述
     *
     * @return plan_desc 方案描述
     */
    public String getPlanDesc() {
        return planDesc;
    }

    /**
     * 方案描述
     *
     * @param planDesc 方案描述
     */
    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    /**
     * 方案类型
     *
     * @return plan_type 方案类型
     */
    public Integer getPlanType() {
        return planType;
    }

    /**
     * 方案类型
     *
     * @param planType 方案类型
     */
    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    /**
     * 覆盖逻辑仓（JSON数组）
     *
     * @return cover_logical_plant 覆盖逻辑仓（JSON数组）
     */
    public String getCoverLogicalPlant() {
        return coverLogicalPlant;
    }

    /**
     * 覆盖逻辑仓（JSON数组）
     *
     * @param coverLogicalPlant 覆盖逻辑仓（JSON数组）
     */
    public void setCoverLogicalPlant(String coverLogicalPlant) {
        this.coverLogicalPlant = coverLogicalPlant;
    }

    /**
     * 覆盖物料类型（0-全部物料/1-指定物料）
     *
     * @return cover_material_type 覆盖物料类型（0-全部物料/1-指定物料）
     */
    public Integer getCoverMaterialType() {
        return coverMaterialType;
    }

    /**
     * 覆盖物料类型（0-全部物料/1-指定物料）
     *
     * @param coverMaterialType 覆盖物料类型（0-全部物料/1-指定物料）
     */
    public void setCoverMaterialType(Integer coverMaterialType) {
        this.coverMaterialType = coverMaterialType;
    }

    /**
     * 计划展望期(天)
     *
     * @return plan_horizon 计划展望期(天)
     */
    public Integer getPlanHorizon() {
        return planHorizon;
    }

    /**
     * 计划展望期(天)
     *
     * @param planHorizon 计划展望期(天)
     */
    public void setPlanHorizon(Integer planHorizon) {
        this.planHorizon = planHorizon;
    }

    /**
     * 计划开始时间
     *
     * @return plan_start_time 计划开始时间
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * 计划开始时间
     *
     * @param planStartTime 计划开始时间
     */
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     * 需求计划文件（JSON结构）
     *
     * @return demand_plan_file 需求计划文件（JSON结构）
     */
    public String getDemandPlanFile() {
        return demandPlanFile;
    }

    /**
     * 需求计划文件（JSON结构）
     *
     * @param demandPlanFile 需求计划文件（JSON结构）
     */
    public void setDemandPlanFile(String demandPlanFile) {
        this.demandPlanFile = demandPlanFile;
    }

    /**
     * 状态（0-已失效/1-已生效）
     *
     * @return status 状态（0-已失效/1-已生效）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（0-已失效/1-已生效）
     *
     * @param status 状态（0-已失效/1-已生效）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

}