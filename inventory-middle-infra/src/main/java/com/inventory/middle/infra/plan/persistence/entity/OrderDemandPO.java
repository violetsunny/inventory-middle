package com.inventory.middle.infra.plan.persistence.entity;

import java.util.Date;

/**
 * 订单需求计划表
 */
public class OrderDemandPO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 来源类型，1:项目订单
     */
    private Integer orderType;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 计划日期
     */
    private Date planDate;

    /**
     * 计划数量
     */
    private Long amount;

    /**
     * 扩展信息
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getLogicalPlantNo() {
        return logicalPlantNo;
    }

    public void setLogicalPlantNo(String logicalPlantNo) {
        this.logicalPlantNo = logicalPlantNo;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
