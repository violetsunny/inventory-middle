package com.inventory.middle.infra.plan.persistence.entity;

import java.util.Date;

/**
 * pl_demand_plan_material_detail
 *
 * @date 2021-10-13
 */
public class DemandPlanMaterialDetailPO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 物料单位
     */
    private String materialUnit;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 计划日期
     */
    private Date planDate;

    /**
     * 数量
     */
    private Long amount;

    /**
     * 数据类型1:预测需求 2：订单需求 3:冲销
     */
    private Integer type;

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

    /**
     * 租户id
     */
    private String tenantId;

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
     * 物料编码
     * 
     * @return material_code 物料编码
     */
    public String getMaterialCode() {
        return materialCode;
    }

    /**
     * 物料编码
     * 
     * @param materialCode 物料编码
     */
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    /**
     * 物料描述
     * 
     * @return material_desc 物料描述
     */
    public String getMaterialDesc() {
        return materialDesc;
    }

    /**
     * 物料描述
     * 
     * @param materialDesc 物料描述
     */
    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    /**
     * 物料单位
     * 
     * @return material_unit 物料单位
     */
    public String getMaterialUnit() {
        return materialUnit;
    }

    /**
     * 物料单位
     * 
     * @param materialUnit 物料单位
     */
    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    /**
     * 逻辑仓编码
     * 
     * @return logical_plant_no 逻辑仓编码
     */
    public String getLogicalPlantNo() {
        return logicalPlantNo;
    }

    /**
     * 逻辑仓编码
     * 
     * @param logicalPlantNo 逻辑仓编码
     */
    public void setLogicalPlantNo(String logicalPlantNo) {
        this.logicalPlantNo = logicalPlantNo;
    }

    /**
     * 逻辑仓名称
     * 
     * @return logical_plant_name 逻辑仓名称
     */
    public String getLogicalPlantName() {
        return logicalPlantName;
    }

    /**
     * 逻辑仓名称
     * 
     * @param logicalPlantName 逻辑仓名称
     */
    public void setLogicalPlantName(String logicalPlantName) {
        this.logicalPlantName = logicalPlantName;
    }

    /**
     * 计划日期
     * 
     * @return plan_date 计划日期
     */
    public Date getPlanDate() {
        return planDate;
    }

    /**
     * 计划日期
     * 
     * @param planDate 计划日期
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    /**
     * 数量
     * 
     * @return amount 数量
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 数量
     * 
     * @param amount 数量
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 数据类型1:预测需求 2：订单需求 3:冲销
     * 
     * @return type 数据类型1:预测需求 2：订单需求 3:冲销
     */
    public Integer getType() {
        return type;
    }

    /**
     * 数据类型1:预测需求 2：订单需求 3:冲销
     * 
     * @param type 数据类型1:预测需求 2：订单需求 3:冲销
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     * 
     * @return is_delete 是否已删除（0-未删除/1-已删除，默认为0）
     */
    public Integer getDeleted() {
        return isDelete;
    }

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     * 
     * @param isDelete 是否已删除（0-未删除/1-已删除，默认为0）
     */
    public void setDeleted(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 创建时间（默认当前时间）
     * 
     * @return create_time 创建时间（默认当前时间）
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间（默认当前时间）
     * 
     * @param createTime 创建时间（默认当前时间）
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间（默认当前时间）
     * 
     * @return update_time 更新时间（默认当前时间）
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间（默认当前时间）
     * 
     * @param updateTime 更新时间（默认当前时间）
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 租户id
     * 
     * @return tenant_id 租户id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户id
     * 
     * @param tenantId 租户id
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
