package com.inventory.middle.infra.plan.persistence.entity;

import java.util.Date;

/**
 * 需求计划周期表
 */
public class DemandPlanMaterialPeriodPO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 需求计划主键
     */
    private Long demandPlanId;

    /**
     * 需求计划物料主键,关联pl_demand_plan_material表
     */
    private Long demandPlanMaterialId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 计划日期描述,格式形如2021/9/28-2021/10/3
     */
    private String planPeriod;

    /**
     * 预测需求数量
     */
    private Long planAmount;

    /**
     * 状态（0-已失效/1-已生效/2-已剔除，默认为1）
     */
    private Integer status;

    /**
     * 1:预测 2：项目订单
     */
    private Integer demandType;

    /**
     * 扩展信息json
     */
    private String extInfo;

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public Long getDemandPlanId() {
        return demandPlanId;
    }

    public void setDemandPlanId(Long demandPlanId) {
        this.demandPlanId = demandPlanId;
    }

    public Long getDemandPlanMaterialId() {
        return demandPlanMaterialId;
    }

    public void setDemandPlanMaterialId(Long demandPlanMaterialId) {
        this.demandPlanMaterialId = demandPlanMaterialId;
    }

    public String getLogicalPlantNo() {
        return logicalPlantNo;
    }

    public void setLogicalPlantNo(String logicalPlantNo) {
        this.logicalPlantNo = logicalPlantNo;
    }

    public String getPlanPeriod() {
        return planPeriod;
    }

    public void setPlanPeriod(String planPeriod) {
        this.planPeriod = planPeriod;
    }

    public Long getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(Long planAmount) {
        this.planAmount = planAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDemandType() {
        return demandType;
    }

    public void setDemandType(Integer demandType) {
        this.demandType = demandType;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public Integer getDeleted() {
        return isDelete;
    }

    public void setDeleted(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
