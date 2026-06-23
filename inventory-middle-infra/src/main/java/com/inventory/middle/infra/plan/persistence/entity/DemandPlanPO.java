package com.inventory.middle.infra.plan.persistence.entity;

import java.util.Date;

/**
 * 需求计划表
 */
public class DemandPlanPO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 公司主体
     */
    private String companyCode;

    /**
     * 公司名字
     */
    private String companyName;

    /**
     * 需求计划版本号
     */
    private String planVersion;

    /**
     * 1:手工导入 2：系统对接
     */
    private Integer demandSourceType;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 物理仓编码
     */
    private String warehouseNo;

    /**
     * 物理仓名称
     */
    private String warehouseName;

    /**
     * 需求汇总周期1:日,2:周,3:月,默认1
     */
    private Integer aggregationPeriod;

    /**
     * 需求展望期开始时间
     */
    private Date demandHorizonBeginTime;

    /**
     * 需求展望期结束时间
     */
    private Date demandHorizonEndTime;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUserId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 1:预测
     */
    private Integer demandType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPlanVersion() {
        return planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion;
    }

    public Integer getDemandSourceType() {
        return demandSourceType;
    }

    public void setDemandSourceType(Integer demandSourceType) {
        this.demandSourceType = demandSourceType;
    }

    public String getLogicalPlantNo() {
        return logicalPlantNo;
    }

    public void setLogicalPlantNo(String logicalPlantNo) {
        this.logicalPlantNo = logicalPlantNo;
    }

    public String getLogicalPlantName() {
        return logicalPlantName;
    }

    public void setLogicalPlantName(String logicalPlantName) {
        this.logicalPlantName = logicalPlantName;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getAggregationPeriod() {
        return aggregationPeriod;
    }

    public void setAggregationPeriod(Integer aggregationPeriod) {
        this.aggregationPeriod = aggregationPeriod;
    }

    public Date getDemandHorizonBeginTime() {
        return demandHorizonBeginTime;
    }

    public void setDemandHorizonBeginTime(Date demandHorizonBeginTime) {
        this.demandHorizonBeginTime = demandHorizonBeginTime;
    }

    public Date getDemandHorizonEndTime() {
        return demandHorizonEndTime;
    }

    public void setDemandHorizonEndTime(Date demandHorizonEndTime) {
        this.demandHorizonEndTime = demandHorizonEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreatorId() {
        return createUserId;
    }

    public void setCreatorId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatorId() {
        return updateUserId;
    }

    public void setUpdatorId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDemandType() {
        return demandType;
    }

    public void setDemandType(Integer demandType) {
        this.demandType = demandType;
    }
}
